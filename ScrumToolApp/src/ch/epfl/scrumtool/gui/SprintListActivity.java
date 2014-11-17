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
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintListActivity extends BaseListMenuActivity<Project> implements OnMenuItemClickListener {

    private Project project;
    
    private ListView listView;
    private SprintListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_list);
        
        initProject();
        
        setTitle(R.string.title_activity_sprint_list);
        
        project.loadSprints(new DefaultGUICallback<List<Sprint>>(this) {

            @Override
            public void interactionDone(List<Sprint> sprintList) {
                adapter = new SprintListAdapter(SprintListActivity.this, sprintList);
                listView = (ListView) findViewById(R.id.sprintList);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(SprintListActivity.this, "Sprint clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.notifyDataSetChanged();
            }
            
        });
    }
    
    public void createSprint(View view) {
        Intent createSprintIntent = new Intent(this, SprintActivity.class);
        createSprintIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(createSprintIntent);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_context, menu);
    }
    
    private void initProject() {
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
    }

    @Override
    void openEditElementActivity(Project optionalElementToEdit) {
        // TODO Auto-generated method stub
        Toast.makeText(SprintListActivity.this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }
}
