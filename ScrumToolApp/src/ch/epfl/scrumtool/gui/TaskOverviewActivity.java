package ch.epfl.scrumtool.gui;

import java.util.List;

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
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.gui.components.widgets.Slate;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class TaskOverviewActivity extends BaseMenuActivity<Issue> {

    private TextView nameView;
    private TextView descriptionView;
    private PrioritySticker prioritySticker;
    private Slate statusSlate;
    private Slate estimationSlate;
    private ListView listView;
    
    private MainTask task;
    private IssueListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

        task = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);
        
        DefaultGUICallback<List<Issue>> issuesLoaded = new DefaultGUICallback<List<Issue>>(this) {
            
            @Override
            public void interactionDone(final List<Issue> issueList) {
                adapter = new IssueListAdapter(TaskOverviewActivity.this, issueList);
                listView = (ListView) findViewById(R.id.task_issues_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        startActivity(openIssueIntent);
                    }
                });
                
                adapter.notifyDataSetChanged();
            }
        };
        
        Client.getScrumClient().loadIssues(task, issuesLoaded);
        
        initViews();
        updateViews();
    }

    private void initViews() {
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (PrioritySticker) findViewById(R.id.task_priority);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null); // TODO right way to do it? (cyriaque)
    }

    private void updateViews() {
        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        prioritySticker.setPriority(task.getPriority());
        statusSlate.setText(task.getStatus().toString());

        float estimatedTime = task.getEstimatedTime();
        estimationSlate.setText(estimatedTime < 0 ? "?" : Float.toString(estimatedTime) + " hours");
    }

    @Override
    void openEditElementActivity(Issue issue) {
        Intent openIssueEditIntent = new Intent(this, IssueEditActivity.class);
        openIssueEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, this.task);
        startActivity(openIssueEditIntent);
    }

}
