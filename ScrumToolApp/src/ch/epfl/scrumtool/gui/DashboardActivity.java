package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DashboardIssueListAdapter;
import ch.epfl.scrumtool.gui.components.DashboardProjectListAdapter;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 * @author Cyriaque Brousse
 */
public class DashboardActivity extends BaseMenuActivity {
    
    // Issues
    private ListView issueListView;
    private TextView issueListEmptyView;
    private DashboardIssueListAdapter issueAdapter;
    
    // Projects
    private GridView projectListView;
    private LinearLayout projectListEmptyView;
    private DashboardProjectListAdapter projectAdapter;
    
    // Buttons
    @SuppressWarnings("unused")
    private Button seeAllProjectButton;

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
    

    private Callback<List<TaskIssueProject>> issuesCallback = new DefaultGUICallback<List<TaskIssueProject>>(this) {
        @Override
        public void interactionDone(final List<TaskIssueProject> containerList) {
            final List<Issue> issueList = new ArrayList<Issue>(containerList.size());
            ListIterator<TaskIssueProject> it = containerList.listIterator();
            while (it.hasNext()) {
                issueList.add(it.nextIndex(), it.next().getIssue());
            }
            
            issueAdapter = new DashboardIssueListAdapter(DashboardActivity.this, issueList);
            issueListView.setAdapter(issueAdapter);

            if (!issueList.isEmpty()) {
                issueListView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        TaskIssueProject container = containerList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, container.getIssue());
                        openIssueIntent.putExtra(Project.SERIALIZABLE_NAME, container.getProject());
                        openIssueIntent.putExtra(MainTask.SERIALIZABLE_NAME, container.getMainTask());
                        startActivity(openIssueIntent);
                    }
                });
            }
            issueAdapter.notifyDataSetChanged();
        }
    };
    
    private Callback<List<Project>> projectsCallback = new DefaultGUICallback<List<Project>>(this) {
        @Override
        public void interactionDone(final List<Project> projectList) {

            List<Project> displayedProjectList = projectList;
            if (projectList.size() == 1) {
                projectListView.setNumColumns(1);
            } else if (projectList.size() > 2) {
                displayedProjectList = projectList.subList(0, 2);
            }
            
            projectAdapter = new DashboardProjectListAdapter(DashboardActivity.this, displayedProjectList);
            projectListView.setAdapter(projectAdapter);

            projectAdapter.notifyDataSetChanged();
        }
    };
    
    public void openAddProject(View view) {
        Intent openProjectEditIntent = new Intent(this, ProjectEditActivity.class);
        startActivity(openProjectEditIntent);
    }

    public void openBacklog(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0 && position != AdapterView.INVALID_POSITION) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
            openBacklogIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openBacklogIntent);
        }
    }
    
    public void openSprints(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0 && position != AdapterView.INVALID_POSITION) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openSprintsIntent = new Intent(this, SprintListActivity.class);
            openSprintsIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openSprintsIntent);
        }
    }
    
    public void openPlayers(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0 && position != AdapterView.INVALID_POSITION) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openPlayerListIntent = new Intent(this, ProjectPlayerListActivity.class);
            openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openPlayerListIntent);
        }
    }
    
    private void initViews() {
        // Buttons
        seeAllProjectButton = (Button) findViewById(R.id.dashboard_button_project_list);
        
        // Issue List
        issueListView = (ListView) findViewById(R.id.dashboard_issue_summary);
        issueListEmptyView = (TextView) findViewById(R.id.dashboard_issue_summary_empty);
        issueListView.setEmptyView(issueListEmptyView);
        
        // Project List
        projectListView = (GridView) findViewById(R.id.dashboard_project_summary);
        projectListEmptyView = (LinearLayout) findViewById(R.id.dashboard_project_summary_empty);
        projectListView.setEmptyView(projectListEmptyView);
        
        ImageView addImage = (ImageView) findViewById(R.id.dashboard_project_summary_add_image);
        addImage.setColorFilter(getResources().getColor(R.color.DarkGray));
    }
    
    private void updateViews() {
        try {
            Session.getCurrentSession().getUser().loadIssuesForUser(issuesCallback);
            Client.getScrumClient().loadProjects(projectsCallback);
        } catch (NotAuthenticatedException e) {
            Session.relogin(this);
        }
    }
}
