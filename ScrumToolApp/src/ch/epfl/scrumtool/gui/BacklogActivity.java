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
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends Activity {
    
    private ListView listView;
    
    private TaskListAdapter adapter;
    private List<MainTask> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        
        // Get list and initialize adapter
        adapter = new TaskListAdapter(this, taskList);
        listView = (ListView) findViewById(R.id.backlog_tasklist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO actually pass a task to the intent
                Intent openTaskIntent = new Intent(view.getContext(), TaskOverviewActivity.class);
                startActivity(openTaskIntent);
            }
        });
        
        // Populate list and update the adapter
        dummyPopulateList();
        adapter.notifyDataSetChanged();
    }

    @Deprecated
    private void dummyPopulateList() {
        taskList.add(Entity.TASK_A);
        taskList.add(Entity.TASK_B);
        taskList.add(Entity.TASK_C);
        taskList.add(Entity.TASK_D);
        taskList.add(Entity.TASK_E);
    }
}
