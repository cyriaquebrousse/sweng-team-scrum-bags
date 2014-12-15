package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.ProjectListAdapter;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class ProjectListActivity extends BaseListMenuActivity<Project> {

    private ListView listView;
    private ProjectListAdapter adapter;
    private SwipeRefreshLayout listViewLayout;
    private SwipeRefreshLayout emptyViewLayout;

    private Callback<List<Project>> loadProjectCallback = new DefaultGUICallback<List<Project>>(this) {
        @Override
        public void interactionDone(final List<Project> projectList) {
            ProjectListActivity that = ProjectListActivity.this;
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);
            
            adapter = new ProjectListAdapter(that, projectList);
            listView.setEmptyView(emptyViewLayout);
            listView.setAdapter(adapter);

            if (!projectList.isEmpty()) {
                registerForContextMenu(listView);
            } else {
                emptyViewLayout.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
        }
        
        @Override
        public void failure(String errorMessage) {
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);
            super.failure(errorMessage);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        
        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_project_list);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_project_list);
        onCreateSwipeToRefresh(emptyViewLayout);
        
        emptyViewLayout.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.project_list);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        emptyViewLayout.setRefreshing(true);
        Client.getScrumClient().loadProjects(loadProjectCallback);
    }
    
    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                
                Client.getScrumClient().loadProjects(loadProjectCallback);
            }
        });
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
        switch (item.getItemId()) {
            case R.id.action_entity_edit:
                openEditElementActivity(adapter.getItem(info.position));
                return true;
            case R.id.action_entity_delete:
                deleteProject(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    @Override
    void openEditElementActivity(Project project) {
        Intent openProjectEditIntent = new Intent(this, ProjectEditActivity.class);
        openProjectEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openProjectEditIntent);
    }

    /**
     * @param project
     *            the project to delete
     */
    private void deleteProject(final Project project) {
        new AlertDialog.Builder(this).setTitle("Delete Project")
            .setMessage("Do you really want to delete this Project? "
                    + "If you are admin, this will remove the Project and all its Tasks, Issues, Players and Sprints."
                    + "If not, you will only be unsubscribed from it.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(true);
                    emptyViewLayout.setRefreshing(true);
                    final Context context = ProjectListActivity.this;
                    project.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            listViewLayout.setRefreshing(false);
                            emptyViewLayout.setRefreshing(false);
                            Toast.makeText(context , "Project deleted", Toast.LENGTH_SHORT).show();
                            adapter.remove(project);
                        }
                        
                        @Override
                        public void failure(String errorMessage) {
                            listViewLayout.setRefreshing(false);
                            emptyViewLayout.setRefreshing(false);
                            super.failure(errorMessage);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }
    
    public void openBacklog(View view) {
        final int position = listView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) listView.getAdapter().getItem(position);
            Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
            openBacklogIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openBacklogIntent);
        }
    }
    
    public void openSprints(View view) {
        final int position = listView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) listView.getAdapter().getItem(position);
            Intent openSprintsIntent = new Intent(this, SprintListActivity.class);
            openSprintsIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openSprintsIntent);
        }
    }
    
    public void openPlayers(View view) {
        final int position = listView.getPositionForView(view);
        if (position >= 0) {
            Project project = (Project) listView.getAdapter().getItem(position);
            Intent openPlayerListIntent = new Intent(this, ProjectPlayerListActivity.class);
            openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, project);
            startActivity(openPlayerListIntent);
        }
    }
}