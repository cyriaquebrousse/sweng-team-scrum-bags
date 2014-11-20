package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;

/**
 * @author AlexVeuthey
 *
 */
public class SprintOverviewActivity extends BaseOverviewMenuActivity {

    private Sprint sprint;
    private Project project;
    
    private TextView name;
    private TextView deadline;
    private ListView listView;
    private IssueListAdapter adapter;
    
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
                adapter = new IssueListAdapter(SprintOverviewActivity.this, issueList);
                listView = (ListView) findViewById(R.id.sprint_overview_issue_list);
                registerForContextMenu(listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        startActivity(openIssueIntent);
                    }
                });
                
                adapter.notifyDataSetChanged();
            }
            
        });
        
        initViews();
        updateViews();
    }
    
    private void initViews() {
        name = (TextView) findViewById(R.id.sprint_overview_name);
        deadline = (TextView) findViewById(R.id.sprint_overview_deadline);
        
        name.setText(sprint.getTitle());
        setDeadlineText();
    }
    
    private void updateViews() {
        name = (TextView) findViewById(R.id.sprint_overview_name);
        deadline = (TextView) findViewById(R.id.sprint_overview_deadline);
        
        name.setText(sprint.getTitle());
        setDeadlineText();
    }
    
    @SuppressLint("SimpleDateFormat")
    private void setDeadlineText() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(sprint.getDeadline());
        deadline.setText(sdf.format(date.getTime()));
    }
    
    // TODO add issues to the Sprint
    
    private void initActivity() {
        sprint = (Sprint) getIntent().getSerializableExtra(Sprint.SERIALIZABLE_NAME);
        if (sprint == null) {
            throw new NullPointerException("Clicked Sprint cannot be null");
        }
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
    }

    @Override
    void openEditElementActivity() {
        Intent openSprintEditIntent = new Intent(this, SprintEditActivity.class);
        openSprintEditIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        openSprintEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openSprintEditIntent);
        updateViews();
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
        updateViews();
    }
}
