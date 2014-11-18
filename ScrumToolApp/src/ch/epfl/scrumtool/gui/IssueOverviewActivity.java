package ch.epfl.scrumtool.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.components.widgets.Stamp;

/**
 * @author Cyriaque Brousse
 */
public class IssueOverviewActivity extends BaseOverviewMenuActivity {

    private TextView nameView;
    private TextView descriptionView;
    private TextView statusView;
    private Stamp estimationStamp;
    private TextView assigneeName;
    private TextView sprintView;

    private Issue issue;
    private MainTask parentTask;
    private Project parentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_overview);

        issue = (Issue) getIntent().getSerializableExtra(Issue.SERIALIZABLE_NAME);
        parentProject = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        parentTask = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);

        nameView = (TextView) findViewById(R.id.issue_name);
        descriptionView = (TextView) findViewById(R.id.issue_desc);
        statusView = (TextView) findViewById(R.id.issue_status);
        estimationStamp = (Stamp) findViewById(R.id.issue_estimation_stamp);
        assigneeName = (TextView) findViewById(R.id.issue_assignee_name);
        sprintView = (TextView) findViewById(R.id.issue_sprint_label);
        
//        assigneeName.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openProfileIntent = new Intent(v.getContext(), ProfileOverviewActivity.class);
//                User assignee = issue.getPlayer().getUser();
//                openProfileIntent.putExtra(User.SERIALIZABLE_NAME, assignee);
//                startActivity(openProfileIntent);
//            }
//        });

        updateViews();
    }

    private void updateViews() {
        nameView.setText(issue.getName());
        descriptionView.setText(issue.getDescription());
        statusView.setText(issue.getStatus().toString());
//        assigneeName.setText(issue.getPlayer().getUser().getName());
//        assigneeName.setPaintFlags(assigneeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        sprintView.setText(issue.getSprint().getTitle());
        estimationStamp.setQuantity(Float.toString(issue.getEstimatedTime()));
        estimationStamp.setUnit(getResources().getString(R.string.project_default_unit));
        estimationStamp.setColor(getResources().getColor(issue.getStatus().getColorRef()));
    }

    @Override
    void openEditElementActivity() {
        Intent openIssueEditIntent = new Intent(this, IssueEditActivity.class);
        openIssueEditIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
        openIssueEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, parentTask);
        openIssueEditIntent.putExtra(Project.SERIALIZABLE_NAME, parentProject);
        startActivity(openIssueEditIntent);
        
    }

    @Override
    void deleteElement() {
        // TODO Auto-generated method stub
        
    }
}
