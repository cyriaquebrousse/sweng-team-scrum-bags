package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.IssueInterface;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.Task;
import ch.epfl.scrumtool.entity.TaskInterface;
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
    
    private TaskInterface task;
    private IssueListAdapter adapter;
    
    @Deprecated
    // TODO this should not be a member of this class
    private List<IssueInterface> issuesList = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        
        // Get the task
        dummyPopulate();
        issuesList = setToList(task.getIssues());
        
        // Get list and initialize adapter
        adapter = new IssueListAdapter(this, issuesList);
        listView = (ListView) findViewById(R.id.task_issues_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO actually pass an issue to the activity
                Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                startActivity(openIssueIntent);
            }
        });
        
        // Get other views
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (Sticker) findViewById(R.id.task_priority);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
        
        // Update the views and the adapter
        updateViews();
        adapter.notifyDataSetChanged();
    }

    private void updateViews() {
        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        prioritySticker.setStickerText(task.getPriority());
        prioritySticker.setColor(getResources().getColor(task.getPriority().getColorRef()));
        statusSlate.setText(task.getStatus().toString());
        
        // TODO don't assume that estimation := sum (issues.estimation) !
        float estimation = 0f;
        for (IssueInterface i : issuesList) {
            estimation += i.getEstimation();
        }
        estimationSlate.setText(Float.toString(estimation));
    }

    @Deprecated
    private void dummyPopulate() {
        Set<IssueInterface> issues = new HashSet<>();
        issues.add(Entity.ISSUE_A1);
        issues.add(Entity.ISSUE_A2);
        issues.add(Entity.ISSUE_B2);
        issues.add(Entity.ISSUE_C1);
        task = new Task("Find meaning of the universe", "It is very important", issues,
                Status.IN_SPRINT, Priority.HIGH);
    }
    
    private <E> List<E> setToList(Set<E> set) {
        List<E> list = new ArrayList<>();
        for (E e : set) {
            list.add(e);
        }
        return list;
    }
}
