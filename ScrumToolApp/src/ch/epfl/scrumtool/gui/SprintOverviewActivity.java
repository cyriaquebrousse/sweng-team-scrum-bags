package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DatePickerFragment;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
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
    private ListView listView;
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
        
        sprint.loadIssues(new DefaultGUICallback<List<Issue>>(this) {

            @Override
            public void interactionDone(final List<Issue> issueList) {
                issueListAdapter = new IssueListAdapter(SprintOverviewActivity.this, issueList);
                listView = (ListView) findViewById(R.id.sprint_overview_issue_list);
                registerForContextMenu(listView);
                listView.setAdapter(issueListAdapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        startActivity(openIssueIntent);
                    }
                });
                
                issueListAdapter.notifyDataSetChanged();
            }
            
        });
        
        project.loadUnsprintedIssues(new DefaultGUICallback<List<Issue>>(this) {
            @Override
            public void interactionDone(List<Issue> issueList) {
                if (issueList != null && !issueList.isEmpty()) {
                    unsprintedIssues = false;
                    
                    issueList.add(0, null);
                    issueSpinnerAdapter = new IssueListAdapter(SprintOverviewActivity.this, issueList);
                    issueSpinner.setAdapter(issueSpinnerAdapter);
                    issueSpinner.setSelection(0);

                    addIssueVisible(View.VISIBLE);
                } else {
                    unsprintedIssues = true;
                }
            }
        });
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null);
    }
    
    private void addIssueVisible(int visibility) {
        TextView issueAdd = (TextView) findViewById(R.id.sprint_overview_issue_add);
        issueAdd.setVisibility(visibility);
        issueSpinner.setVisibility(visibility);
    }
    
    private void initViews() {
        nameView = (TextView) findViewById(R.id.sprint_overview_name);
        deadlineView = (TextView) findViewById(R.id.sprint_overview_deadline);
        issueSpinner = (Spinner) findViewById(R.id.issue_spinner);
        
        if (unsprintedIssues) {
            issueBuilder = new Issue.Builder((Issue) issueSpinner.getSelectedItem());
            issueBuilder.setSprint(sprint);
            updateIssue();
        }
        
        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(SprintOverviewActivity.this, "name",
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
        
        nameView.setText(sprint.getTitle());
        setDeadlineText();
        setTitle(sprint.getTitle());
        
        // set the visibility to GONE by defaut so that it only shows up when the unsprinted Issues list is ready
        addIssueVisible(View.GONE);
    }
    
    private void setDeadlineText() {
        SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                .getString(R.string.format_date), Locale.ENGLISH);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        deadlineView.setText(sdf.format(date.getTime()));
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
            sprint = (Sprint) data.getSerializableExtra(Sprint.SERIALIZABLE_NAME);
            throwIfNull("Sprint cannot be null", sprint);
            initViews();
        }
    }

    @Override
    void deleteElement() {
        sprint.remove(new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                if (success) {
                    SprintOverviewActivity.this.finish();
                } else {
                    Toast.makeText(SprintOverviewActivity.this, "Could not delete sprint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void updateIssue() {
        issue = issueBuilder.build();
        issue.update(null, new DefaultGUICallback<Boolean>(SprintOverviewActivity.this) {
            @Override
            public void interactionDone(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(SprintOverviewActivity.this, 
                            "Could not add issue to sprint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void updateSprint() {
        sprint = sprintBuilder.build();
        sprint.update(null, new DefaultGUICallback<Boolean>(SprintOverviewActivity.this) {
            @Override
            public void interactionDone(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(SprintOverviewActivity.this, 
                            "Could not update sprint", Toast.LENGTH_SHORT).show();
                }
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
