package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

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
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.SprintListAdapter;

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
    
    private void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                project.loadSprints(callback);
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
        sprint.remove(new DefaultGUICallback<Void>(this) {
            @Override
            public void interactionDone(Void v) {
                listViewLayout.setRefreshing(false);
                adapter.remove(sprint);
            }
        });
    }

    @Override
    void openEditElementActivity(Sprint sprint) {
        Intent openSprintEditIntent = new Intent(this, SprintEditActivity.class);
        openSprintEditIntent.putExtra(Sprint.SERIALIZABLE_NAME, sprint);
        openSprintEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openSprintEditIntent);
    }
}
