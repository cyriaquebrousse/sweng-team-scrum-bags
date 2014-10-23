package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.DummyIssue;
import ch.epfl.scrumtool.entity.IssueInterface;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.widgets.Stamp;

/**
 * @author Cyriaque Brousse
 */
public class IssueOverviewActivity extends Activity {

    private TextView nameView;
    private TextView descriptionView;
    private TextView statusView;
    private Stamp estimationStamp;
    private TextView assigneeName;

    private IssueInterface issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_overview);

        // Get views
        nameView = (TextView) findViewById(R.id.issue_name);
        descriptionView = (TextView) findViewById(R.id.issue_desc);
        statusView = (TextView) findViewById(R.id.issue_status);
        estimationStamp = (Stamp) findViewById(R.id.issue_estimation_stamp);
        assigneeName = (TextView) findViewById(R.id.issue_assignee_name);

        // Create dummy issue and update views
        dummyPopulate();
        updateViews();
    }

    private void updateViews() {
        nameView.setText(issue.getName());
        descriptionView.setText(issue.getDescription());
        statusView.setText(issue.getStatus().toString());
        estimationStamp.setQuantity(Float.toString(issue.getEstimation()));
        estimationStamp.setUnit(getResources().getString(R.string.project_default_unit));
        estimationStamp.setColor(getResources().getColor(issue.getStatus().getColorRef()));
        assigneeName.setText(issue.getAssignedPlayer().getAccount().getName());
    }

    @Deprecated
    private void dummyPopulate() {
        this.issue = new DummyIssue("Create new GUI class",
                "Create java class for the sprint display",
                Status.READY_FOR_SPRINT, 2f);
    }
}
