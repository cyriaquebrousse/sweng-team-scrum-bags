package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author AlexVeuthey
 *
 */
public class SprintListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_list);
    }
    
    public void createSprint(View view) {
        Intent createSprintIntent = new Intent(this, SprintActivity.class);
        startActivity(createSprintIntent);
    }
}
