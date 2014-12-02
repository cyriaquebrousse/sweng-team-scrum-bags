package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.entityNameIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.textEditNonNullNotEmpty;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.util.InputVerifiers;

/**
 * @author Cyriaque Brousse
 */
public class ProjectEditActivity extends BaseMenuActivity {
    
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

    public void saveProjectChanges(View view) {
        
        boolean titleIsValid = entityNameIsValid(projectTitleView);
        boolean descriptionIsValid = textEditNonNullNotEmpty(projectDescriptionView);
        
        InputVerifiers.updateTextViewAfterValidityCheck(projectTitleView, titleIsValid, getResources());
        InputVerifiers.updateTextViewAfterValidityCheck(projectDescriptionView, descriptionIsValid, getResources());
        
        if (titleIsValid && descriptionIsValid) {
            findViewById(R.id.project_edit_button_next).setEnabled(false);
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
        Project project = projectBuilder.build();
        project.insert(new DefaultGUICallback<Project>(this) {
            @Override
            public void interactionDone(Project object) {
                ProjectEditActivity.this.finish();
            }
        });
    }

    private void updateProject() {
        Project project = projectBuilder.build();
        project.update(null, new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    ProjectEditActivity.this.finish();
                } else {
                    Toast.makeText(ProjectEditActivity.this, "Could not update project", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.project_edit_button_next).setEnabled(true);
                }                
            }
        });
    }
}
