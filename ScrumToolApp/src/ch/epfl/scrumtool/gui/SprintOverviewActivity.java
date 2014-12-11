package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.IssueListAdapter;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.FieldType;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

/**
 * @author AlexVeuthey, sylb
 */
public class SprintOverviewActivity extends BaseListMenuActivity<Issue> implements OnMenuItemClickListener {

    // Entities
    private Sprint sprint;
    private Project project;
    private Sprint.Builder sprintBuilder;
    private List<Issue> unsprintedIssues;
    
    // Views
    private static TextView nameView;
    private static TextView deadlineView;
    private ListView issueListView;
    private IssueListAdapter issueListAdapter;
    private IssueListAdapter issueNoSprintAdapter;
    private SwipeRefreshLayout listViewLayout;
    private SwipeRefreshLayout emptyViewLayout;
    
    // Callbacks
    private Callback<List<Issue>> loadIssuesCallback = new DefaultGUICallback<List<Issue>>(this) {
        @Override
        public void interactionDone(final List<Issue> issueList) {
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);
            issueListAdapter = new IssueListAdapter(SprintOverviewActivity.this, issueList);
            issueListView.setAdapter(issueListAdapter);
            if (!issueList.isEmpty()) {
                registerForContextMenu(issueListView);
                issueListView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        openIssueIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                        startActivity(openIssueIntent);
                    }
                });
            } else {
                emptyViewLayout.setVisibility(View.VISIBLE);
            }
            issueListAdapter.notifyDataSetChanged();
        }
    };
    private Callback<List<Issue>> loadUnsprintedIssuesCallback = new DefaultGUICallback<List<Issue>>(this) {
        @Override
        public void interactionDone(List<Issue> unsprintedIssuesList) {
            if (unsprintedIssuesList == null || unsprintedIssuesList.isEmpty()) {
                new AlertDialog.Builder(SprintOverviewActivity.this)
                    .setTitle("No Issue found")
                    .setMessage("This project has no unassigned issues.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            } else {
                unsprintedIssues = new ArrayList<Issue>(unsprintedIssuesList);
                displayIssueSelectionPopup();
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_overview);
        
        initValues();
        initViews();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        sprint.loadIssues(loadIssuesCallback);
    }
    
    private void initViews() {
        nameView = (TextView) findViewById(R.id.sprint_overview_name);
        deadlineView = (TextView) findViewById(R.id.sprint_overview_deadline);
        issueListView = (ListView) findViewById(R.id.sprint_overview_issue_list);
    
        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(SprintOverviewActivity.this, FieldType.NAMEFIELD,
                        nameView.getText().toString(), new PopupCallback<String>() {
                            @Override
                            public void onModified(String userInput) {
                                sprintBuilder = new Sprint.Builder(sprint);
                                sprintBuilder.setTitle(userInput);
                                nameView.setText(userInput);
                                updateSprint();
                            }
                        });
            }
        });
        
        deadlineView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(deadlineView, new DefaultGUICallback<Calendar>(SprintOverviewActivity.this) {
    
                    @Override
                    public void interactionDone(Calendar deadline) {
                        sprintBuilder = new Sprint.Builder(sprint);
                        sprintBuilder.setDeadline(deadline.getTimeInMillis());
                        String stringDeadline = convertDeadlineToString(deadline);
                        deadlineView.setText(stringDeadline);
                        updateSprint();
                    }
                });
            }
        });
        
        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_sprint_issue_list);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_sprint_issue_list);
        onCreateSwipeToRefresh(emptyViewLayout);
    
        emptyViewLayout.setVisibility(View.INVISIBLE);
        
        nameView.setText(sprint.getTitle());
        setDeadlineText();
    }

    private void initValues() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        throwIfNull("Sprint cannot be null", sprint);
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent project cannot be null", project);
        unsprintedIssues = new ArrayList<Issue>();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_context, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final Issue issue = issueListAdapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.action_entity_edit:
                openEditElementActivity(issue);
                return true;
            case R.id.action_entity_delete:
                removeIssueFromSprint(issue);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    @Override
    public void openEditElementActivity(Issue issue) {
        Intent openIssueEditIntent = new Intent(this, IssueEditActivity.class);
        openIssueEditIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
        openIssueEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openIssueEditIntent);
    }
    
    private void removeIssueFromSprint(final Issue issue) {
        listViewLayout.setRefreshing(true);
        new AlertDialog.Builder(this).setTitle("Delete Issue")
            .setMessage("Do you really want to delete this Issue from this Sprint? "
                    + "This will remove the Issue from this Sprints but not from the Project.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = SprintOverviewActivity.this;
                    issue.getBuilder().setSprint(null).build().update(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            Toast.makeText(context , "Issue removed from Sprint", Toast.LENGTH_SHORT).show();
                            listViewLayout.setRefreshing(false);
                            issueListAdapter.remove(issue);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(false);
                }
            }).show();
    }
    
    public void openCreateElementActivity() {
        project.loadUnsprintedIssues(loadUnsprintedIssuesCallback);
    }
    
    private void displayIssueSelectionPopup() {
        issueNoSprintAdapter = new IssueListAdapter(SprintOverviewActivity.this, unsprintedIssues);
        new AlertDialog.Builder(this)
            .setTitle("Add Issue to Sprint")
            .setAdapter(issueNoSprintAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Issue issue = unsprintedIssues.get(which).getBuilder().setSprint(sprint).build();
                    issue.update(
                           new DefaultGUICallback<Void>(SprintOverviewActivity.this) {
    
                            @Override
                            public void interactionDone(Void object) {
                                issueListAdapter.add(issue);
                            }
                        });
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }

    private void updateViews() {
        nameView.setText(sprint.getTitle());
        setDeadlineText();
    }
    
    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                sprint.loadIssues(loadIssuesCallback);
                refreshLayout.setRefreshing(false);
            }
        });
    }
    
    private void setDeadlineText() {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        deadlineView.setText(sdf.format(date.getTime()));
        deadlineView.postInvalidate();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                sprint = (Sprint) data.getSerializableExtra(Sprint.SERIALIZABLE_NAME);
                throwIfNull("Sprint cannot be null", sprint);
                updateViews();
            }
        }
    }

    
    private void updateSprint() {
        sprint = sprintBuilder.build();
        sprint.update(new DefaultGUICallback<Void>(SprintOverviewActivity.this) {
            @Override
            public void interactionDone(Void v) {
            }
        });
    }
    
    public void showDatePickerDialog(View v, final DefaultGUICallback<Calendar> callback) {
        Calendar oldDate = Calendar.getInstance();
        oldDate.setTimeInMillis(sprint.getDeadline());
        OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
                Calendar chosen = Calendar.getInstance();
                chosen.set(Calendar.YEAR, year);
                chosen.set(Calendar.MONTH, monthOfYear);
                chosen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                callback.interactionDone(chosen);
            }
        };
        new DatePickerDialog(SprintOverviewActivity.this, dateListener, oldDate.get(Calendar.YEAR),
                oldDate.get(Calendar.MONTH), oldDate.get(Calendar.DAY_OF_MONTH)).show();
                
    }

    private String convertDeadlineToString(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        return sdf.format(date.getTime());
    }

}
