package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.IssueListAdapter;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.FieldType;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

/**
 * @author AlexVeuthey, sylb
 */
public class SprintOverviewActivity extends BaseOverviewMenuActivity {

    // Entities
    private Sprint sprint;
    private Project project;
    private Sprint.Builder sprintBuilder;
    
    private Issue issue;
    private Issue.Builder issueBuilder;
    private boolean unsprintedIssues;
    
    // Calendar
    private Calendar chosen = Calendar.getInstance();
    private final Calendar today = Calendar.getInstance();
    private long sprintDeadline = today.getTimeInMillis();
    
    // Views
    private static TextView nameView;
    private static TextView deadlineView;
    private ListView issueListView;
    private IssueListAdapter issueListAdapter;
    private IssueListAdapter issueSpinnerAdapter;
    private Spinner issueSpinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_overview);
        
        initActivity();
        initViews();
        
        setTitle(sprint.getTitle());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        sprint.loadIssues(new DefaultGUICallback<List<Issue>>(this) {
            @Override
            public void interactionDone(final List<Issue> issueList) {
                issueListAdapter = new IssueListAdapter(SprintOverviewActivity.this, issueList);
                issueListView = (ListView) findViewById(R.id.sprint_overview_issue_list);
                registerForContextMenu(issueListView);
                issueListView.setAdapter(issueListAdapter);
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
                
                issueListAdapter.notifyDataSetChanged();
            }
            
        });
        
        project.loadUnsprintedIssues(new DefaultGUICallback<List<Issue>>(this) {
            @Override
            public void interactionDone(List<Issue> unsprintedIssuesList) {
                if (unsprintedIssuesList != null && !unsprintedIssuesList.isEmpty()) {
                    unsprintedIssuesList.add(0, null);
                    unsprintedIssues = true;
                    issueSpinnerAdapter = new IssueListAdapter(SprintOverviewActivity.this, unsprintedIssuesList);
                    issueSpinner.setAdapter(issueSpinnerAdapter);
                    issueSpinner.setSelection(0);
                    addIssueVisible(View.VISIBLE);
                } else {
                    unsprintedIssues = false;
                }
                initSpinner();
            }
        });
    }
    
    private void addIssueVisible(int visibility) {
        TextView issueAdd = (TextView) findViewById(R.id.sprint_overview_issue_add);
        issueAdd.setVisibility(visibility);
        issueSpinner.setVisibility(visibility);
        
        issueAdd.postInvalidate();
        issueSpinner.postInvalidate();
    }
    
    private void initSpinner() {
        issueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                issue = (Issue) issueSpinner.getItemAtPosition(position);
                if (unsprintedIssues && issue != null) {
                    issueBuilder = new Issue.Builder(issue);
                    issueBuilder.setSprint(sprint);
                    issueBuilder.setStatus(Status.IN_SPRINT);
                    updateIssue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    
    private void initViews() {
        nameView = (TextView) findViewById(R.id.sprint_overview_name);
        deadlineView = (TextView) findViewById(R.id.sprint_overview_deadline);
        issueSpinner = (Spinner) findViewById(R.id.issue_spinner);
        
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
                                setTitle(userInput);
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
        
        nameView.setText(sprint.getTitle());
        setDeadlineText();
        setTitle(sprint.getTitle());
        
        // set the visibility to GONE by defaut so that it only shows up when the unsprinted Issues list is ready
        addIssueVisible(View.GONE);
    }
    
    private void refreshViews() {
        nameView.setText(sprint.getTitle());
        setDeadlineText();
        setTitle(sprint.getTitle());
    }
    
    private void setDeadlineText() {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        deadlineView.setText(sdf.format(date.getTime()));
        deadlineView.postInvalidate();
    }
    
    private void initActivity() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        throwIfNull("Sprint cannot be null", sprint);
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent project cannot be null", project);
    }

    @Override
    void openEditElementActivity() {
        Intent openSprintEditIntent = new Intent(this, SprintEditActivity.class);
        openSprintEditIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        openSprintEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivityForResult(openSprintEditIntent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                sprint = (Sprint) data.getSerializableExtra(Sprint.SERIALIZABLE_NAME);
                throwIfNull("Sprint cannot be null", sprint);
                refreshViews();
            }
        }
    }

    @Override
    void deleteElement() {
        new AlertDialog.Builder(this).setTitle("Delete Sprint")
            .setMessage("Do you really want to delete this Sprint? "
                    + "This will remove the sprint and all its links with Issues.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = SprintOverviewActivity.this;
                    sprint.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            Toast.makeText(context , "Sprint deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            })
            .setNegativeButton(android.R.string.no, null).show();
    }
    
    private void updateIssue() {
        final Issue issueToBeAdded = issue;
        issue = issueBuilder.build();
        issue.update(new DefaultGUICallback<Void>(SprintOverviewActivity.this) {
            @Override
            public void interactionDone(Void v) {
                ArrayList<Issue> list = (ArrayList<Issue>) issueSpinnerAdapter.getList();
                list.remove(issueToBeAdded);
                issueListAdapter.add(issue);
                issueSpinner.setSelection(0);
                if (list.size() < 2) {
                    addIssueVisible(View.GONE);
                    unsprintedIssues = false;
                }
            }
        });
        
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
        DialogFragment newFragment = new DatePickerFragment() {
            
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                chosen.set(Calendar.YEAR, year);
                chosen.set(Calendar.MONTH, monthOfYear);
                chosen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sprintDeadline = chosen.getTimeInMillis();
                callback.interactionDone(chosen);
                deadlineView.setText(convertDeadlineToString(chosen));
            }
        };
        Bundle args = new Bundle();
        args.putLong("long", sprintDeadline);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private String convertDeadlineToString(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        return sdf.format(date.getTime());
    }

}
