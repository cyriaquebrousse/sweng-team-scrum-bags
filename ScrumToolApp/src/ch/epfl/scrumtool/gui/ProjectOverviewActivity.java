package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;

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

        project = (Project) getIntent().getSerializableExtra(
                Project.SERIALIZABLE_NAME);

        nameView = (TextView) findViewById(R.id.project_title);
        descriptionView = (TextView) findViewById(R.id.project_description);

        nameView.setText(project.getName());
        descriptionView.setText(project.getDescription());
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
}
