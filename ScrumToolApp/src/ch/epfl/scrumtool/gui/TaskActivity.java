package ch.epfl.scrumtool.gui;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
	}
}
