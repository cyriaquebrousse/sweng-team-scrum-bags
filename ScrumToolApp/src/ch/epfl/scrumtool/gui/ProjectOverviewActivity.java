package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class ProjectOverviewActivity extends Activity {

    private TextView nameView;
    private TextView descriptionView;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_overview);
        
        Intent intent = getIntent();
        
        // Get the project
        long projectId = intent.getLongExtra("project_id", 0);
        project = ServerSimulator.getProjectById(projectId);

        // Get Views
        nameView = (TextView) findViewById(R.id.project_title);
        descriptionView = (TextView) findViewById(R.id.project_description);

        // Set Views
        nameView.setText(project.getName());
        descriptionView.setText(project.getDescription());
        
    }

    public void openBacklog(View view) {
        Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
        
        // Pass the project Id
        openBacklogIntent.putExtra("project_id", project.getId());
        
        startActivity(openBacklogIntent);
    }
}
