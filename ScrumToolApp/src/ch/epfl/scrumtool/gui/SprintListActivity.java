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
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.SprintListAdapter;

/**
 * @author AlexVeuthey
 */
public class SprintListActivity extends BaseListMenuActivity<Sprint> implements OnMenuItemClickListener {

    private Project project;
    
    private ListView listView;
    private SwipeRefreshLayout listViewLayout;
    private SwipeRefreshLayout emptyViewLayout;
    private SprintListAdapter adapter;
    
    private DefaultGUICallback<List<Sprint>> callback = new DefaultGUICallback<List<Sprint>>(this) {

        @Override
        public void interactionDone(final List<Sprint> sprintList) {
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);

            adapter = new SprintListAdapter(SprintListActivity.this, sprintList);
            listView.setEmptyView(emptyViewLayout);
            listView.setAdapter(adapter);

            if (!sprintList.isEmpty()) {
                registerForContextMenu(listView);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openSprintOverviewIntent = new Intent(
                                view.getContext(),
                                SprintOverviewActivity.class);

                        Sprint sprint = sprintList.get(position);
                        openSprintOverviewIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
                        openSprintOverviewIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                        startActivity(openSprintOverviewIntent);
                    }
                });
            } else {
                emptyViewLayout.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
        }
        
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_list);
        
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent project cannot be null", project);
        
        this.setTitle(project.getName());

        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_sprint_list);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_sprint_list);
        onCreateSwipeToRefresh(emptyViewLayout);

        listView = (ListView) findViewById(R.id.sprint_list);
        
        emptyViewLayout.setVisibility(View.INVISIBLE);

        project.loadSprints(callback);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        project.loadSprints(callback);
    }
    
    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                project.loadSprints(callback);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void openCreateElementActivity() {
        Intent createSprintIntent = new Intent(this, SprintEditActivity.class);
        createSprintIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(createSprintIntent);
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
                deleteSprint(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteSprint(final Sprint sprint) {
        listViewLayout.setRefreshing(true);
        new AlertDialog.Builder(this).setTitle("Delete Sprint")
            .setMessage("Do you really want to delete this Sprint? "
                    + "This will remove the Sprint and all its links with Issues.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = SprintListActivity.this;
                    sprint.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            Toast.makeText(context , "Sprint deleted", Toast.LENGTH_SHORT).show();
                            listViewLayout.setRefreshing(false);
                            adapter.remove(sprint);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(false);
                }
            }).show();
    }

    @Override
    void openEditElementActivity(Sprint sprint) {
        Intent openSprintEditIntent = new Intent(this, SprintEditActivity.class);
        openSprintEditIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        openSprintEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openSprintEditIntent);
    }
}
