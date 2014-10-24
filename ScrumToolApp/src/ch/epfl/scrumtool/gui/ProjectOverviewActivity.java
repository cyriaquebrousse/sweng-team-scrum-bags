package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public class ProjectOverviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_overview);
        
        Intent intent = getIntent();
        String projectName = intent.getStringExtra("project_name");
        String projectDescription = intent.getStringExtra("project_description");
        
    }

    public void openBacklog(View view) {
        Intent openBacklogIntent = new Intent(this, BacklogActivity.class);
        startActivity(openBacklogIntent);
    }
}
