package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.gui.components.widgets.Stamp;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author Cyriaque Brousse
 */
public class IssueOverviewActivity extends Activity {

    private TextView nameView;
    private TextView descriptionView;
    private TextView statusView;
    private Stamp estimationStamp;
    private TextView assigneeName;

    private Issue issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_overview);

        Intent intent = getIntent();
        
        // Get the task
        long issueId = intent.getLongExtra("issue_id", 0);
        issue = ServerSimulator.getIssueById(issueId);

        // Get views
        nameView = (TextView) findViewById(R.id.issue_name);
        descriptionView = (TextView) findViewById(R.id.issue_desc);
        statusView = (TextView) findViewById(R.id.issue_status);
        estimationStamp = (Stamp) findViewById(R.id.issue_estimation_stamp);
        assigneeName = (TextView) findViewById(R.id.issue_assignee_name);

        // Set views
        updateViews();
    }

    private void updateViews() {
        nameView.setText(issue.getName());
        descriptionView.setText(issue.getDescription());
        statusView.setText(issue.getStatus().toString());
        assigneeName.setText(issue.getPlayer().getUser().getName());
        estimationStamp.setQuantity(Float.toString(issue.getEstimatedTime()));
        estimationStamp.setUnit(getResources().getString(R.string.project_default_unit));
        estimationStamp.setColor(getResources().getColor(issue.getStatus().getColorRef()));
    }

}
