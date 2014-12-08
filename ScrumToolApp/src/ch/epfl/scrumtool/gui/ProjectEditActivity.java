package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.verifyDescriptionIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyNameIsValid;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;

/**
 * @author Cyriaque Brousse
 */
public class ProjectEditActivity extends BaseEditMenuActivity {
    
    private EditText projectTitleView;
    private EditText projectDescriptionView;
    
    /** If {@code null} then we are in create mode, otherwise in edit mode*/
    private Project original;
    private Project.Builder projectBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        
        initOriginal();
        initViews();
    }
    
    @Override
    protected void saveElement() {
        saveProjectChanges();
    }

    private void initOriginal() {
        original = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (original == null) {
            projectBuilder = new Project.Builder();
            setTitle(R.string.title_activity_project_edit_new);
        } else {
            projectBuilder = new Project.Builder(original);
        }
    }
    
    private void initViews() {
        projectTitleView = (EditText) findViewById(R.id.project_title_edit);
        projectDescriptionView = (EditText) findViewById(R.id.project_description_edit);
        
        projectTitleView.setText(projectBuilder.getName());
        projectDescriptionView.setText(projectBuilder.getDescription());
    }

    /**
     * Saves changes to Project
     * @param view
     */
    private void saveProjectChanges() {
        
        Resources resources = getResources();
        boolean titleIsValid = verifyNameIsValid(projectTitleView, resources);
        boolean descriptionIsValid = verifyDescriptionIsValid(projectDescriptionView, resources);
        
        if (titleIsValid && descriptionIsValid) {
            findViewById(Menu.FIRST).setEnabled(false);
            String newTitle = projectTitleView.getText().toString();
            String newDescription = projectDescriptionView.getText().toString();
            
            projectBuilder.setName(newTitle);
            projectBuilder.setDescription(newDescription);
            
            if (original == null) {
                insertProject();
            } else {
                updateProject();
            }
        }
    }
    
    private void insertProject() {
        final Project project = projectBuilder.build();
        final View next = findViewById(Menu.FIRST);
        project.insert(new DefaultGUICallback<Project>(this, next) {
            @Override
            public void interactionDone(Project object) {
                ProjectEditActivity.this.finish();
            }
        });
    }

    private void updateProject() {
        final Project project = projectBuilder.build();
        final View next = findViewById(Menu.FIRST);
        project.update(new DefaultGUICallback<Void>(this, next) {
            @Override
            public void interactionDone(Void v) {
                ProjectEditActivity.this.finish();
            }
        });
    }
}
