package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.gui.components.DummyProject;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;

public class ProjectsActivity extends Activity {

	private List<DummyProject> projectsList = new ArrayList<DummyProject>();
	private ListView listView;
	private ProjectListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);
		
		// Get list and initialize its adapter
		adapter = new ProjectListAdapter(this, projectsList);
		listView = (ListView) findViewById(R.id.project_list);
		listView.setAdapter(adapter);
		
		// Create some dummy projects and add them to the list
		dummyPopulate();
		adapter.notifyDataSetChanged();
		
		for (DummyProject p : projectsList) {
			System.out.println(p.getName());
		}
	}
	
	@Deprecated
	/** Demo purposes only! **/
	private void dummyPopulate() {
		projectsList.add(new DummyProject("Scrum", "Scrum management app", 5));
		projectsList.add(new DummyProject("HelloWorld", "Super fancy hello world program", 3));
		projectsList.add(new DummyProject("NoIdeaProject", "This is a description! And it is very very long", 0));
	}
}
