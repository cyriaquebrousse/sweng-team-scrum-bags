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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends BaseListMenuActivity<MainTask> implements OnMenuItemClickListener {

    private ListView listView;
    private Project project;
    private TaskListAdapter adapter;
    private SwipeRefreshLayout listViewLayout;
    private SwipeRefreshLayout emptyViewLayout;

    private final DefaultGUICallback<List<MainTask>> callback = new DefaultGUICallback<List<MainTask>>(this) {
        @Override
        public void interactionDone(final List<MainTask> taskList) {
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);

            adapter = new TaskListAdapter(BacklogActivity.this, taskList);
            listView.setEmptyView(emptyViewLayout);
            listView.setAdapter(adapter);

            if (!taskList.isEmpty()) {
                registerForContextMenu(listView);
            } else {
                emptyViewLayout.setVisibility(View.VISIBLE);
            }

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent openTaskOverviewIntent = new Intent(view.getContext(),
                            TaskOverviewActivity.class);

                    MainTask mainTask = taskList.get(position);
                    openTaskOverviewIntent.putExtra(MainTask.SERIALIZABLE_NAME, mainTask);
                    openTaskOverviewIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                    startActivity(openTaskOverviewIntent);
                }
            });

            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        listView = (ListView) findViewById(R.id.backlog_tasklist);

        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        this.setTitle(project.getName());

        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_backlog);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_backlog_tasklist);
        onCreateSwipeToRefresh(emptyViewLayout);

        emptyViewLayout.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.backlog_tasklist);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        project.loadBacklog(callback);
    }
    
    private void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                project.loadBacklog(callback);
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                deleteMainTask(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    /**
     * @param project
     *            the project to delete
     */
    private void deleteMainTask(final MainTask mainTask) {
        listViewLayout.setRefreshing(true);
        mainTask.remove(new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                listViewLayout.setRefreshing(false);
                adapter.remove(mainTask);
            }
        });
    }
    
    @Override
    void openEditElementActivity(MainTask task) {
        Intent openTaskEditIntent = new Intent(this, TaskEditActivity.class);
        openTaskEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
        openTaskEditIntent.putExtra(Project.SERIALIZABLE_NAME, this.project);
        startActivity(openTaskEditIntent);
    }
}
