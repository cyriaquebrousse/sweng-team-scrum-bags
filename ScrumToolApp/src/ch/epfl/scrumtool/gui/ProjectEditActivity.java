package ch.epfl.scrumtool.gui;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    
    private Project.Builder projectBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        
        // Get project and construct a builder from it
        Project fromIntent = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (fromIntent == null) {
            projectBuilder = new Project.Builder();
            setTitle(R.string.title_activity_project_edit_new);
        } else {
            projectBuilder = new Project.Builder(fromIntent);
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
            Client.getScrumClient().insertProject(project, new Callback<Project>() {
                @Override
                public void interactionDone(Project object) {
                    ProjectEditActivity.this.finish();
                }
            });
        }
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
