package ch.epfl.scrumtool.gui;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class ProjectEditActivity extends Activity {
    
    private EditText projectTitleView;
    private EditText projectDescriptionView;
    
    /** If {@code null} then we are in create mode, otherwise in edit mode*/
    private Project original;
    private Project.Builder projectBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        
        // Get project and construct a builder from it
        original = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (original == null) {
            projectBuilder = new Project.Builder();
            setTitle(R.string.title_activity_project_edit_new);
        } else {
            projectBuilder = new Project.Builder(original);
        }
        
        // Get the views and initialize them with the builder values
        projectTitleView = (EditText) findViewById(R.id.project_title_edit);
        projectDescriptionView = (EditText) findViewById(R.id.project_description_edit);
        
        projectTitleView.setText(projectBuilder.getName());
        projectDescriptionView.setText(projectBuilder.getDescription());
    }
    
    public void saveProjectChanges(View view) {
        updateTextViewAfterValidityCheck(projectTitleView, titleIsValid());
        updateTextViewAfterValidityCheck(projectDescriptionView, descriptionIsValid());
        
        if (titleIsValid() && descriptionIsValid()) {
            String newTitle = projectTitleView.getText().toString();
            String newDescription = projectDescriptionView.getText().toString();
            
            projectBuilder.setName(newTitle);
            projectBuilder.setDescription(newDescription);
            projectBuilder.setId("this is a random id "+ new Random().nextInt()); // FIXME project id
            
            Project project = projectBuilder.build();
            
            if (original == null) {
                insertProject(project);
            } else {
                updateProject(project);
            }
        }
    }
    
    private void insertProject(Project project) {
        Client.getScrumClient().insertProject(project, new Callback<Project>() {
            @Override
            public void interactionDone(Project object) {
                ProjectEditActivity.this.finish();
            }
        });
    }

    private void updateProject(Project project) {
        Client.getScrumClient().updateProject(project, original, new Callback<Boolean>() {
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    ProjectEditActivity.this.finish();
                } else {
                    Toast.makeText(ProjectEditActivity.this, "Could not update project", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean titleIsValid() {
        String title = projectTitleView.getText().toString();
        return title != null && title.length() > 0
                && title.length() < 50; // TODO put a meaningful value (cyriaque)
    }

    private boolean descriptionIsValid() {
        String description = projectDescriptionView.getText().toString();
        return description != null && description.length() > 0;
    }
    
    /**
     * Warn the user that incorrect input was entered in the specified text
     * field
     * 
     * @param view
     *            the text field in which the error sign will be displayed
     */
    private void updateTextViewAfterValidityCheck(EditText view, boolean inputValid) {
        if (!inputValid) {
            view.setError(getResources().getString(R.string.error_field_required));
        } else {
            view.setError(null);
        }
    }
}
