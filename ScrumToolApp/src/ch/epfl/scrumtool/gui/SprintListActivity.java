package ch.epfl.scrumtool.gui;

import java.util.List;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.SprintListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintListActivity extends BaseListMenuActivity<Sprint> implements OnMenuItemClickListener {

    private Project project;
    
    private ListView listView;
    private SprintListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_list);
        
        initProject();
        
        setTitle(R.string.title_activity_sprint_list);

        final View progressBar = findViewById(R.id.waiting_sprint_list);
        listView = (ListView) findViewById(R.id.sprintList);
        listView.setEmptyView(progressBar);

        project.loadSprints(new DefaultGUICallback<List<Sprint>>(this) {

            @Override
            public void interactionDone(final List<Sprint> sprintList) {
                if (sprintList.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    View emptyList = findViewById(R.id.empty_sprint_list);
                    listView.setEmptyView(emptyList);
                }

                adapter = new SprintListAdapter(SprintListActivity.this, sprintList);
                registerForContextMenu(listView);
                listView.setAdapter(adapter);

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

                adapter.notifyDataSetChanged();
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
    
    private void initProject() {
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
    }
    
    private void deleteSprint(final Sprint sprint) {
        sprint.remove(new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
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
