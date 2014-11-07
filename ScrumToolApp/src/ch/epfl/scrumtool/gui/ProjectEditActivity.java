package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import ch.epfl.scrumtool.R;

/**
 * @author Cyriaque Brousse
 */
public class ProjectEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
    }
}
