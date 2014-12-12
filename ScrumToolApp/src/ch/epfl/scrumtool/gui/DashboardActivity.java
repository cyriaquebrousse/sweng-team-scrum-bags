package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.DashboardIssueListAdapter;
import ch.epfl.scrumtool.gui.components.adapters.DashboardProjectListAdapter;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        initViews();
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
        Intent intent = new Intent(this, MyProfileOverviewActivity.class);
        startActivity(intent);
    }
    

    private Callback<List<TaskIssueProject>> issuesCallback = new DefaultGUICallback<List<TaskIssueProject>>(this) {
        @Override
        public void interactionDone(final List<TaskIssueProject> containerList) {
            
            issueAdapter = new DashboardIssueListAdapter(DashboardActivity.this, containerList);
            issueListView.setAdapter(issueAdapter);

            if (!containerList.isEmpty()) {
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
            } else if (projectList.size() >= 2) {
                projectListView.setNumColumns(2);
            } 
            if (projectList.size() > 2) {
                displayedProjectList = projectList.subList(0, 2);
            }
            
            projectAdapter = new DashboardProjectListAdapter(DashboardActivity.this, displayedProjectList);
            projectListView.setAdapter(projectAdapter);

            projectAdapter.notifyDataSetChanged();
        }
    };
    
    private Callback<List<Player>> playersCallback = new DefaultGUICallback<List<Player>>(this) {

        @Override
        public void interactionDone(List<Player> players) {
            for (final Player p : players) {
                //open dialog asking to accept/refuse p.project invitation
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                dialogBuilder.setTitle("Project invitation");
                dialogBuilder.setMessage("You have been invited to participate in project \"" 
                        + p.getProject().getName() + "\". \n"
                        + "What are you gonna do?");
                dialogBuilder.setPositiveButton("Join the fun", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        p.getBuilder().setIsInvited(false).build().update(
                                new DefaultGUICallback<Void>(DashboardActivity.this) {

                                @Override
                                public void interactionDone(Void object) {
                                    Toast.makeText(DashboardActivity.this, "Project Joined", Toast.LENGTH_SHORT).show();
                                    updateViews();
                                }
                            });
                    }
                });
                dialogBuilder.setNegativeButton("Work alone", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        p.remove(new DefaultGUICallback<Void>(DashboardActivity.this) {

                            @Override
                            public void interactionDone(Void object) {
                                // TODO Auto-generated method stub
                                Toast.makeText(DashboardActivity.this, "invitation denied", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialogBuilder.create().show();
            }
        }
    };
    
    public void openAddProject(View view) {
        Intent openProjectEditIntent = new Intent(this, ProjectEditActivity.class);
        startActivity(openProjectEditIntent);
    }

    public void openBacklog(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
            openBacklogIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openBacklogIntent);
        }
    }
    
    public void openSprints(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openSprintsIntent = new Intent(this, SprintListActivity.class);
            openSprintsIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openSprintsIntent);
        }
    }
    
    public void openPlayers(View view) {
        final int position = projectListView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) projectListView.getAdapter().getItem(position);
            Intent openPlayerListIntent = new Intent(this, ProjectPlayerListActivity.class);
            openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openPlayerListIntent);
        }
    }
    
    private void initViews() {
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
            Session.getCurrentSession().getUser().loadInvitedPlayers(playersCallback);
            Session.getCurrentSession().getUser().loadIssuesForUser(issuesCallback);
            Client.getScrumClient().loadProjects(projectsCallback);
        } catch (NotAuthenticatedException e) {
            Session.relogin(this);
        }
    }
}
