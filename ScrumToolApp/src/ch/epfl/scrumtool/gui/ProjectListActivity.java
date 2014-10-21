package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.ProjectInterface;
import ch.epfl.scrumtool.gui.components.DummyProject;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;

/**
 * @author cyriaquebrousse
 */
public class ProjectListActivity extends Activity {

    private List<ProjectInterface> projectsList = new ArrayList<>();
    private ListView listView;
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

        // Get list and initialize its adapter
        adapter = new ProjectListAdapter(this, projectsList);
        listView = (ListView) findViewById(R.id.project_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "You clicked position"+ position, Toast.LENGTH_SHORT).show();
                Intent openProjectOverviewIntent = new Intent(view.getContext(), ProjectOverviewActivity.class);
                startActivity(openProjectOverviewIntent);
            }
        });

        // Create some dummy projects and add them to the list
        dummyPopulate();
        adapter.notifyDataSetChanged();

        for (ProjectInterface p : projectsList) {
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
