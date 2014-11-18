package ch.epfl.scrumtool.gui;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
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
        
        /*sprint.loadIssues(new DefaultGUICallback<List<Issue>>(this) {

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
            
        });*/
    }
    
    private void initViews() {
        name = (TextView) findViewById(R.id.sprint_overview_name);
        deadline = (TextView) findViewById(R.id.sprint_overview_deadline);
        
        name.setText(sprint.getTitle());
        deadline.setText(getDeadline(sprint.getDeadline()));
    }
    
    // TODO delete sprint via the toolbar
    
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
    
    private String getDeadline(long deadline) {
        Calendar deadlineAsCal = Calendar.getInstance();
        deadlineAsCal.setTimeInMillis(deadline);
        return "" + deadlineAsCal.get(Calendar.DAY_OF_MONTH) + "/" 
            + deadlineAsCal.get(Calendar.MONTH) + "/" + deadlineAsCal.get(Calendar.YEAR);
    }

    @Override
    void openEditElementActivity() {
        Intent openSprintEditIntent = new Intent(this, SprintEditActivity.class);
        openSprintEditIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        openSprintEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openSprintEditIntent);
    }

    @Override
    void deleteElement() {
        // TODO Auto-generated method stub
        
    }
}
