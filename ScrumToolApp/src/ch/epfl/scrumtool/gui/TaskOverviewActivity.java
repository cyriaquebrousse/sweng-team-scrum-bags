package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.entity.Status.FINISHED;
import static ch.epfl.scrumtool.entity.Status.IN_SPRINT;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_ESTIMATION;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.DummyIssue;
import ch.epfl.scrumtool.entity.IssueInterface;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.Slate;
import ch.epfl.scrumtool.gui.components.widgets.Sticker;

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
    
    private IssueListAdapter adapter;
    private List<IssueInterface> issuesList = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        
        // Get list and initialize adapter
        adapter = new IssueListAdapter(this, issuesList);
        listView = (ListView) findViewById(R.id.task_issues_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "You clicked position "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        
        // Get other views
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (Sticker) findViewById(R.id.task_priority);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
        
        // Create some dummy issues and add them to the list
        dummyPopulate();
        dummyPopulateList();
        adapter.notifyDataSetChanged();
    }

    @Deprecated
    private void dummyPopulateList() {
        issuesList.add(new DummyIssue("Fix bug in class toto", "all in title", READY_FOR_ESTIMATION, 3f));
        issuesList.add(new DummyIssue("dummy issue", "who would have guessed", FINISHED, 1f));
        issuesList.add(new DummyIssue("elle est prête", "description!", READY_FOR_SPRINT, 0.5f));
        issuesList.add(new DummyIssue("last one", "hello world", IN_SPRINT, 2f));
    }
    
    @Deprecated
    private void dummyPopulate() {
        nameView.setText("Big big task");
        descriptionView.setText("Very interesting description");
        prioritySticker.setStickerText(Priority.URGENT);
        statusSlate.setText(Status.READY_FOR_SPRINT.toString());
        estimationSlate.setText("6.5 hours");
    }
}
