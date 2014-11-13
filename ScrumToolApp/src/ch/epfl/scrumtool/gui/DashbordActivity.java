package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;

/**
 * 
 * @author ketsio
 *
 */
public class DashbordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
    }

    public void openProjectList(View view) {
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
    }
    
    public void openMyProfile(View view) {
        Intent intent = new Intent(this, ProfileOverviewActivity.class);
        startActivity(intent);
    }
}
