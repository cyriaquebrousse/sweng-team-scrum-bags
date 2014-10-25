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
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class ProjectListActivity extends Activity {

    private List<Project> projectsList;
    private ListView listView;
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

        // Create some dummy projects and add them to the list
        projectsList = new ArrayList<Project>(ServerSimulator.PROJECTS);
        
        // Get list and initialize its adapter
        adapter = new ProjectListAdapter(this, projectsList);
        listView = (ListView) findViewById(R.id.project_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openProjectOverviewIntent = new Intent(view.getContext(), ProjectOverviewActivity.class);
                
                // Pass the project Id
                Project project = projectsList.get(position);
                openProjectOverviewIntent.putExtra("project_id", project.getId());
                
                startActivity(openProjectOverviewIntent);
            }
        });

        adapter.notifyDataSetChanged();
    }

}
