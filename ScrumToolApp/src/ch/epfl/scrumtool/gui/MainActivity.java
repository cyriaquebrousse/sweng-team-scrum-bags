package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;

/**
 * Note: This class is temporary.
 * @author Cyriaque Brousse
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLogin(View view) {
        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginIntent);
    }
    
    public void openProjects(View view) {
        Intent openProjectsIntent = new Intent(this, ProjectListActivity.class);
        startActivity(openProjectsIntent);
    }
    
    public void openProfileOverview(View view) {
        Intent openProfileOverviewIntent = new Intent(this, ProfileOverviewActivity.class);
        startActivity(openProfileOverviewIntent);
    }
}
