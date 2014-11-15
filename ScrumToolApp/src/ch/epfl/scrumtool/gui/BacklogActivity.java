package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends BaseMenuActivity<MainTask> implements OnMenuItemClickListener {

    private ListView listView;
    private Project project;

    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);

        final DefaultGUICallback<List<MainTask>> maintasksLoaded = new DefaultGUICallback<List<MainTask>>(this) {
            @Override
            public void interactionDone(final List<MainTask> taskList) {
                adapter = new TaskListAdapter(BacklogActivity.this, taskList);

                listView = (ListView) findViewById(R.id.backlog_tasklist);
                registerForContextMenu(listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openTaskOverviewIntent = new Intent(
                                view.getContext(),
                                TaskOverviewActivity.class);

                        MainTask mainTask = taskList.get(position);
                        openTaskOverviewIntent.putExtra(MainTask.SERIALIZABLE_NAME, mainTask);
                        startActivity(openTaskOverviewIntent);
                    }
                });

                adapter.notifyDataSetChanged();
                
            }
        };
        Client.getScrumClient().loadBacklog(project.getKey(), maintasksLoaded);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null); // TODO right way to do this (cyriaque)
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
        Client.getScrumClient().removeMainTask(mainTask.getKey(), new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
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
