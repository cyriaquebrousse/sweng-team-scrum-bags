package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends Activity {
    
    private ListView listView;
    
    private TaskListAdapter adapter;
    private List<MainTask> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        
        Intent intent = getIntent();

        // Get the project
        long projectId = intent.getLongExtra("project_id", 0);
        taskList = ServerSimulator.getBacklogByProjectId(projectId);
        
        // Get list and initialize adapter
        adapter = new TaskListAdapter(this, taskList);
        listView = (ListView) findViewById(R.id.backlog_tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openTaskIntent = new Intent(view.getContext(), TaskOverviewActivity.class);

                // Pass the task Id
                MainTask task = taskList.get(position);
                openTaskIntent.putExtra("task_id", task.getId());
                
                startActivity(openTaskIntent);
            }
        });
        
        adapter.notifyDataSetChanged();
    }
}
