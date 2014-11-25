package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 * @author Cyriaque Brousse
 */
public class DashboardActivity extends BaseMenuActivity {
    
    private ListView issueListView;
    private ListView projectListView;
    private IssueListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        initViews();
        updateViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViews();
    }

    public void openProjectList(View view) {
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
    }
    
    public void openMyProfile(View view) {
        Intent intent = new Intent(this, ProfileOverviewActivity.class);
        startActivity(intent);
    }
    

    private Callback<List<Issue>> issuesCallback = new DefaultGUICallback<List<Issue>>(this) {
        @Override
        public void interactionDone(final List<Issue> issueList) {

            adapter = new IssueListAdapter(DashboardActivity.this, issueList);
            issueListView.setAdapter(adapter);

            if (!issueList.isEmpty()) {
                issueListView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        // TODO : need project and task
                        //openIssueIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                        //openIssueIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
                        startActivity(openIssueIntent);
                    }
                });
            }
            adapter.notifyDataSetChanged();
        }
    };
    
    private void initViews() {
        issueListView = (ListView) findViewById(R.id.dashboard_issue_summary);
        projectListView = (ListView) findViewById(R.id.dashboard_project_summary);
    }
    
    private void updateViews() {
        try {
            Session.getCurrentSession().getUser().loadIssuesForUser(issuesCallback);
        } catch (NotAuthenticatedException e) {
            Session.relogin(this);
        }
    }
}
