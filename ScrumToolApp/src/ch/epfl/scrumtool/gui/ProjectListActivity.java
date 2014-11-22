package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class ProjectListActivity extends BaseListMenuActivity<Project> {

    private ListView listView;
    private ProjectListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private Callback<List<Project>> callback = new DefaultGUICallback<List<Project>>(this) {
        @Override
        public void interactionDone(final List<Project> projectList) {
            swipeLayout.setRefreshing(false);
            adapter = new ProjectListAdapter(ProjectListActivity.this, projectList);
            listView.setAdapter(adapter);

            if (projectList.isEmpty()) {
                TextView emptyList = new TextView(ProjectListActivity.this);
                emptyList.setText("There are no projects");
                emptyList.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                listView.addFooterView(emptyList);
            } else {
                registerForContextMenu(listView);
            }

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent openProjectOverviewIntent = new Intent(
                            view.getContext(),
                            ProjectOverviewActivity.class);
                    // Reduce postion by one to take empty HeaderView into account
                    Project project = projectList.get(position - 1);
                    openProjectOverviewIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                    startActivity(openProjectOverviewIntent);
                }
            });

            adapter.notifyDataSetChanged();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        
        this.setTitle("Projects");
        
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_project_list);
        swipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Client.getScrumClient().loadProjects(callback);
                swipeLayout.setRefreshing(false);
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setRefreshing(true);
        
        listView = (ListView) findViewById(R.id.project_list);
        listView.addFooterView(new View(this));
        listView.addHeaderView(new View(this));

        Client.getScrumClient().loadProjects(callback);
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null); // TODO right way to do it? (cyriaque)
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
                openEditElementActivity(adapter.getItem(info.position-1));
                return true;
            case R.id.action_entity_delete:
                deleteProject(adapter.getItem(info.position-1));
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
        swipeLayout.setRefreshing(true);
        project.remove(new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                swipeLayout.setRefreshing(false);
                adapter.remove(project);
            }
        });
    }
}