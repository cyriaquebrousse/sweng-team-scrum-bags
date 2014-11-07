package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;

/**
 * @author Cyriaque Brousse
 */
public class ProjectEditActivity extends Activity {
    
    private EditText projectTitleView;
    private EditText projectDescriptionView;
    private Button nextButton;
    
    private Project.Builder projectBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        
        // Get project and construct a builder from it
        Project fromIntent = (Project) getIntent().getSerializableExtra("ch.epfl.scrumtool.PROJECT");
        if (fromIntent == null) {
            projectBuilder = new Project.Builder();
        } else {
            projectBuilder = new Project.Builder(fromIntent);
        }
        
        // Get the views and initialize them with the builder values
        projectTitleView = (EditText) findViewById(R.id.project_title_edit);
        projectDescriptionView = (EditText) findViewById(R.id.project_description_edit);
        nextButton = (Button) findViewById(R.id.project_edit_button_next);
        
        nextButton.setEnabled(false);
        projectTitleView.addTextChangedListener(titleVerifier());
        projectDescriptionView.addTextChangedListener(descriptionVerifier());
        
        projectTitleView.setText(projectBuilder.getName());
        projectDescriptionView.setText(projectBuilder.getDescription());
    }
    
    public void saveProjectChanges(View view) {
        String newTitle = projectTitleView.getText().toString();
        String newDescription = projectDescriptionView.getText().toString();
        
        projectBuilder.setName(newTitle);
        projectBuilder.setDescription(newDescription);
        
        Project project = projectBuilder.build();
        
        Toast.makeText(this, "Project (should be) saved", Toast.LENGTH_SHORT).show();
        this.finish(); // TODO save project and then open player list maybe?
    }
    
    private TextWatcher titleVerifier() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            
            @Override
            public void afterTextChanged(Editable s) {
                updateTextViewAfterValidityCheck(projectTitleView, titleIsValid());
                updateNextButtonEnableState();
            }
        };
    }
    
    private TextWatcher descriptionVerifier() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            
            @Override
            public void afterTextChanged(Editable s) {
                updateTextViewAfterValidityCheck(projectDescriptionView, descriptionIsValid());
                updateNextButtonEnableState();
            }
        };
    }

    private boolean titleIsValid() {
        String title = projectTitleView.getText().toString();
        return title != null && title.length() > 0
                && title.length() < 50; // TODO put a meaningful value (cyriaque)
    }

    private void updateNextButtonEnableState() {
        nextButton.setEnabled(titleIsValid() && descriptionIsValid());
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
            view.setTextColor(getResources().getColor(R.color.darkred));
        } else {
            view.setTextColor(getResources().getColor(R.color.Black));
        }
    }
}
