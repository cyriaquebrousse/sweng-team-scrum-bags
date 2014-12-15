package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.TaskListAdapter;

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
        setContentView(R.layout.activity_backlog);

        listView = (ListView) findViewById(R.id.backlog_tasklist);

        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent object cannot be null", project);

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
        emptyViewLayout.setRefreshing(true);
        project.loadBacklog(callback);
    }
    
    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                project.loadBacklog(callback);
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
                deleteMainTask(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    /**
     * Deletes the main task passed as argument
     * 
     * @param mainTask
     *            the task to delete
     */
    private void deleteMainTask(final MainTask mainTask) {
        new AlertDialog.Builder(this).setTitle("Delete Task")
            .setMessage("Do you really want to delete this Task? "
                    + "This will remove the Task and all its Issues.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(true);
                    emptyViewLayout.setRefreshing(true);
                    final Context context = BacklogActivity.this;
                    mainTask.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            listViewLayout.setRefreshing(false);
                            emptyViewLayout.setRefreshing(false);
                            Toast.makeText(context , "Task deleted", Toast.LENGTH_SHORT).show();
                            adapter.remove(mainTask);
                        }
                        
                        @Override
                        public void failure(String errorMessage) {
                            listViewLayout.setRefreshing(true);
                            emptyViewLayout.setRefreshing(true);
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
    
    @Override
    void openEditElementActivity(MainTask task) {
        Intent openTaskEditIntent = new Intent(this, TaskEditActivity.class);
        openTaskEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
        openTaskEditIntent.putExtra(Project.SERIALIZABLE_NAME, this.project);
        startActivity(openTaskEditIntent);
    }
}
