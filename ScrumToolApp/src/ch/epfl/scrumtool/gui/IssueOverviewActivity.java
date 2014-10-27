package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

        // Get the task
        long issueId = getIntent().getLongExtra("ch.epfl.scrumtool.ISSUE_ID", 0);
        issue = ServerSimulator.getIssueById(issueId);

        // Get views
        nameView = (TextView) findViewById(R.id.issue_name);
        descriptionView = (TextView) findViewById(R.id.issue_desc);
        statusView = (TextView) findViewById(R.id.issue_status);
        estimationStamp = (Stamp) findViewById(R.id.issue_estimation_stamp);
        assigneeName = (TextView) findViewById(R.id.issue_assignee_name);
        
        // Set listener on assignee name, so that it opens a profile view
        assigneeName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openProfileIntent = new Intent(v.getContext(), ProfileOverviewActivity.class);
                long assigneeId = issue.getPlayer().getUser().getToken(); // FIXME token != id, or is it?
                openProfileIntent.putExtra("ch.epfl.scrumtool.USER_ID", assigneeId);
                startActivity(openProfileIntent);
            }
        });

        // Set views
        updateViews();
    }

    private void updateViews() {
        nameView.setText(issue.getName());
        descriptionView.setText(issue.getDescription());
        statusView.setText(issue.getStatus().toString());
        assigneeName.setText(issue.getPlayer().getUser().getName());
        assigneeName.setPaintFlags(assigneeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        estimationStamp.setQuantity(Float.toString(issue.getEstimatedTime()));
        estimationStamp.setUnit(getResources().getString(R.string.project_default_unit));
        estimationStamp.setColor(getResources().getColor(issue.getStatus().getColorRef()));
    }
}
