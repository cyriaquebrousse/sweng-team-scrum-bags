package ch.epfl.scrumtool.gui;

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
import ch.epfl.scrumtool.database.google.DSMainTaskHandler;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends Activity {
    
    private ListView listView;
    private Project project;
    
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        
        project = (Project) getIntent().getSerializableExtra("ch.epfl.scrumtool.PROJECT");
        project.loadBacklog(new Callback<List<MainTask>>() {
            
            @Override
            public void interactionDone(final List<MainTask> taskList) {

                // Get list and initialize adapter
                adapter = new TaskListAdapter(BacklogActivity.this, taskList);
                listView = (ListView) findViewById(R.id.backlog_tasklist);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openTaskIntent = new Intent(view.getContext(), TaskOverviewActivity.class);
                        
                        // Pass the task Id
                        MainTask task = taskList.get(position);
                        openTaskIntent.putExtra("ch.epfl.scrumtool.TASK", task);
                        
                        startActivity(openTaskIntent);
                    }
                });
                
                adapter.notifyDataSetChanged();
            }
        });
    }
    
    public void addTask(View view) {
        
        MainTask.Builder tB = new MainTask.Builder();
        tB.setName("Static Task");
        tB.setDescription("I am a super super cool description... YAHOU !");
        tB.setPriority(Priority.HIGH);
        tB.setStatus(Status.READY_FOR_ESTIMATION);
        tB.setId("newTaskId");
        
        final MainTask newTask = tB.build();
        
        DSMainTaskHandler tH = new DSMainTaskHandler();
        tH.insert(newTask, new Callback<Boolean>() {
            
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    
                    // FIXME: need database function project.addTask(task)
                    
                    Intent openTaskOverviewIntent = new Intent(BacklogActivity.this, TaskOverviewActivity.class);
                    openTaskOverviewIntent.putExtra("ch.epfl.scrumtool.TASK", newTask);
                    startActivity(openTaskOverviewIntent);
                } else {
                    Log.e("BacklogActivity", "Could not insert the new task");
                }
            }
        });
    }
}
