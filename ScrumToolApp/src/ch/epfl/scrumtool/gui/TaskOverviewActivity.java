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
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.Slate;
import ch.epfl.scrumtool.gui.components.widgets.Sticker;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class TaskOverviewActivity extends Activity {

    private TextView nameView;
    private TextView descriptionView;
    private Sticker prioritySticker;
    private Slate statusSlate;
    private Slate estimationSlate;
    private ListView listView;
    
    private MainTask task;
    private List<Issue> issueList;
    private IssueListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

        // Get the task
        long taskId = getIntent().getLongExtra("ch.epfl.scrumtool.TASK_ID", 0);
        task = ServerSimulator.getTaskById(taskId);
        
        // FIXME getIssues does not exist in new entity model, use loadIssues
//        issueList = new ArrayList<>(task.getIssues());
        issueList = ServerSimulator.ISSUES;
        
        // Get list and initialize adapter
        adapter = new IssueListAdapter(this, issueList);
        listView = (ListView) findViewById(R.id.task_issues_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);

                // Pass the issue Id
                Issue issue = issueList.get(position);
                openIssueIntent.putExtra("ch.epfl.scrumtool.ISSUE_ID", issue.getId());
                
                startActivity(openIssueIntent);
            }
        });
        
        // Get other views
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (Sticker) findViewById(R.id.task_priority);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
        
        // Set the views
        updateViews();

        adapter.notifyDataSetChanged();
    }

    private void updateViews() {
        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        prioritySticker.setStickerText(task.getPriority());
        prioritySticker.setColor(getResources().getColor(task.getPriority().getColorRef()));
        statusSlate.setText(task.getStatus().toString());

        float estimatedTime = task.getEstimatedTime();
        estimationSlate.setText(estimatedTime < 0 ? "?" : Float.toString(estimatedTime) + " hours");
    }

}
