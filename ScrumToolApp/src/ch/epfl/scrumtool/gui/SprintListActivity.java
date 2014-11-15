package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintListActivity extends BaseMenuActivity<Project> implements OnMenuItemClickListener {

    private Project project;
    
    private ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_list);
        
        initOriginalAndParentTask();
        initViews();
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
    
    private void initViews() {
        listView = (ListView) findViewById(R.id.sprintList);
    }
    
    private void initOriginalAndParentTask() {
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
        setTitle(R.string.title_activity_sprint_list);
    }

    @Override
    void openEditElementActivity(Project optionalElementToEdit) {
        // TODO Auto-generated method stub
        
    }
}
