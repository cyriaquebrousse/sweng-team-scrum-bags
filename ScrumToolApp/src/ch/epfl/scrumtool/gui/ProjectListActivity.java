package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.DSProjectHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Project.Builder;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class ProjectListActivity extends Activity {

    private List<Project> projectList;
    private ListView listView;
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

        // Create some dummy projects and add them to the list
        projectList = new ArrayList<Project>(ServerSimulator.PROJECTS);

        Project.Builder pB = new Project.Builder();
        pB.setDescription("New Project Description");
        pB.setName("My Project");
        pB.setId("testid");
        
        DSProjectHandler pH = new DSProjectHandler();
        final Project p = pB.build();
        pH.insert(p, new Callback<Boolean>() {
            @Override
            public void interactionDone(Boolean object) {
                if (object.booleanValue() == true) {
                    projectList.add(p);
                } else {
                    Log.d("ProjectListActivity", "Project insert not successful");
                }
                
            }
        });

        // Get list and initialize its adapter
        adapter = new ProjectListAdapter(this, projectList);
        listView = (ListView) findViewById(R.id.project_list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openProjectOverviewIntent = new Intent(view.getContext(), ProjectOverviewActivity.class);
                
                // Pass the project Id
                Project project = projectList.get(position);
                openProjectOverviewIntent.putExtra("ch.epfl.scrumtool.PROJECT_ID", project.getId());
                
                startActivity(openProjectOverviewIntent);
            }
        });

        adapter.notifyDataSetChanged();
    }

}
