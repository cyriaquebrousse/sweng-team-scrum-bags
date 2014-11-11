package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.R.id;
import ch.epfl.scrumtool.R.layout;
import ch.epfl.scrumtool.R.string;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class IssueEditActivity extends Activity {
    
    private EditText issueNameView;
    private EditText issueDescriptionView;
    private EditText issueEstimationView;
    private EditText issueAssigneeView;
    
    /** If {@code null} then we are in create mode, otherwise in edit mode*/
    private Issue original;
    private MainTask parentTask;
    private Issue.Builder issueBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_edit);
        
        initOriginalAndParentTask();
        initViews();
    }
    
    private void initOriginalAndParentTask() {
        original = (Issue) getIntent().getSerializableExtra(Issue.SERIALIZABLE_NAME);
        if (original == null) {
            issueBuilder = new Issue.Builder();
            setTitle(R.string.title_activity_issue_edit_new);
        } else {
            issueBuilder = new Issue.Builder(original);
        }
        
        parentTask = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);
        if (parentTask == null) {
            throw new NullPointerException("Parent task cannot be null");
        }
    }
    
    private void initViews() {
        issueNameView = (EditText) findViewById(R.id.issue_name_edit);
        issueDescriptionView = (EditText) findViewById(R.id.issue_description_edit);
        issueEstimationView = (EditText) findViewById(R.id.issue_estimation_edit);
        issueAssigneeView = (EditText) findViewById(R.id.issue_assignee_edit);
        
        issueNameView.setText(issueBuilder.getName());
        issueDescriptionView.setText(issueBuilder.getDescription());
        issueEstimationView.setText(Float.toString(issueBuilder.getEstimatedTime()));
        //issueAssigneeView.setText(issueBuilder.getPlayer().getUser().getName()); //FIXME assignee
    }
    
    public void saveIssueChanges(View view) {
        // TODO auto-generated method stub
    }
    
    private void insertIssue() {
        Issue issue = issueBuilder.build();
        Client.getScrumClient().insertIssue(parentTask, issue, new Callback<Issue>() {
            @Override
            public void interactionDone(Issue object) {
                IssueEditActivity.this.finish();
            }
        });
    }
    
    private void updateIssue() {
        Issue issue = issueBuilder.build();
        Client.getScrumClient().updateIssue(issue, original, new Callback<Boolean>() {
            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    IssueEditActivity.this.finish();
                } else {
                    Toast.makeText(IssueEditActivity.this, "Could not update issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
