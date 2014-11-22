package ch.epfl.scrumtool.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

/**
 * @author Cyriaque Brousse
 */
public class ProjectOverviewActivity extends BaseOverviewMenuActivity {

    private TextView nameView;
    private TextView descriptionView;

    private Project project;
    private Project.Builder projectBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_overview);

        project = (Project) getIntent().getSerializableExtra(
                Project.SERIALIZABLE_NAME);
        this.setTitle(project.getName());

        nameView = (TextView) findViewById(R.id.project_title);
        descriptionView = (TextView) findViewById(R.id.project_description);

        nameView.setText(project.getName());
        descriptionView.setText(project.getDescription());
        
        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(ProjectOverviewActivity.this, new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        projectBuilder = new Project.Builder(project);
                        projectBuilder.setName(userInput);
                        nameView.setText(userInput);
                        updateProject();
                    }
                });
            }
        });
        
        descriptionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(ProjectOverviewActivity.this, new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        projectBuilder = new Project.Builder(project);
                        projectBuilder.setDescription(userInput);
                        descriptionView.setText(userInput);
                        updateProject();
                    }
                });
            }
        });
    }

    public void openBacklog(View view) {
        Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
        openBacklogIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openBacklogIntent);
    }

    public void openPlayerList(View view) {
        Intent openPlayerListIntent = new Intent(this,
                ProjectPlayerListActivity.class);
        openPlayerListIntent.putExtra(Project.SERIALIZABLE_NAME, this.project);
        startActivity(openPlayerListIntent);
    }

    public void openSprints(View view) {
        Intent openSprintsIntent = new Intent(this, SprintListActivity.class);
        openSprintsIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openSprintsIntent);
    }

    @Override
    void openEditElementActivity() {
        Intent openProjectEditIntent = new Intent(this, ProjectEditActivity.class);
        openProjectEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openProjectEditIntent);
    }

    @Override
    void deleteElement() {
        new AlertDialog.Builder(this).setTitle("Delete Project")
            .setMessage("Do you really want to delete this project?")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = ProjectOverviewActivity.this;
                    project.remove(new DefaultGUICallback<Boolean>(context) {
                        @Override
                        public void interactionDone(Boolean success) {
                            Toast.makeText(context, "Project deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            })
            .setNegativeButton(android.R.string.no, null).show();
    }
    
    private void updateProject() {

        projectBuilder.build().update(null, new DefaultGUICallback<Boolean>(ProjectOverviewActivity.this) {
            @Override
            public void interactionDone(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(ProjectOverviewActivity.this, 
                            "Could not update task", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
