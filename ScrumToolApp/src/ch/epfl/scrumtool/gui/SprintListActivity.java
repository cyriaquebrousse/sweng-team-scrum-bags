package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintListActivity extends Activity {

    private Project project;
    
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
    
    private void initViews() {
        
    }
    
    private void initOriginalAndParentTask() {
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project == null) {
            throw new NullPointerException("Parent project cannot be null");
        }
        setTitle(R.string.title_activity_sprint_list);
    }
}
