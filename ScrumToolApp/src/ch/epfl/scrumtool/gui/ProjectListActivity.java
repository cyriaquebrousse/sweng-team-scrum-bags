package ch.epfl.scrumtool.gui;

//import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
//import ch.epfl.scrumtool.database.google.DSProjectHandler;
import ch.epfl.scrumtool.entity.Project;
//import ch.epfl.scrumtool.entity.Project.Builder;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;
//import ch.epfl.scrumtool.network.ServerSimulator;
import ch.epfl.scrumtool.network.Session;

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
        try {
            Session.getCurrentSession().getUser().loadProjects(new Callback<List<Project>>() {

                @Override
                public void interactionDone(List<Project> object) {
                    projectList = object;
                    // Get list and initialize its adapter
                    adapter = new ProjectListAdapter(ProjectListActivity.this, projectList);
                    listView = (ListView) findViewById(R.id.project_list);
                    listView.setAdapter(adapter);
                    
                    listView.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent openProjectOverviewIntent = new Intent(view.getContext(), 
                                    ProjectOverviewActivity.class);
                            
                            // Pass the project Id
                            Project project = projectList.get(position);
                            openProjectOverviewIntent.putExtra("ch.epfl.scrumtool.PROJECT_ID", project.getId());
                            
                            startActivity(openProjectOverviewIntent);
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
            });
        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        Project.Builder pB = new Project.Builder();
//        pB.setDescription("This is a very important Project (Loaded from Server)");
//        pB.setName("Another Project");
//        pB.setId("yowhatup");
//        
//        DSProjectHandler pH = new DSProjectHandler();
//        final Project p = pB.build();
//        pH.insert(p, new Callback<Boolean>() {
//            @Override
//            public void interactionDone(Boolean object) {
//                if (object.booleanValue() == true) {
//                    Log.d("ProjectListActivity", "Project insert successful, horray!!");
//                } else {
//                    Log.d("ProjectListActivity", "Project insert not successful");
//                }
//                
//            }
//        });

    }

}
