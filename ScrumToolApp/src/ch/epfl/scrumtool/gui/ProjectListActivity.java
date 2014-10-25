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
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;

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
        projectsList = new ArrayList<Project>();
        dummyPopulate();
        
        // Get list and initialize its adapter
        adapter = new ProjectListAdapter(this, projectsList);
        listView = (ListView) findViewById(R.id.project_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "You clicked position "+ position, Toast.LENGTH_SHORT).show();
                Intent openProjectOverviewIntent = new Intent(view.getContext(), ProjectOverviewActivity.class);
                
                Project project = projectsList.get(position);
                openProjectOverviewIntent.putExtra("project_name", project.getName());
                openProjectOverviewIntent.putExtra("project_name", project.getDescription());
                
                startActivity(openProjectOverviewIntent);
            }
        });

        adapter.notifyDataSetChanged();
    }

    @Deprecated
    /** Demo purposes only! **/
    private void dummyPopulate() {
        projectsList.add(Entity.COOL_PROJECT);
        projectsList.add(Entity.SUPER_PROJECT);
    }

}
