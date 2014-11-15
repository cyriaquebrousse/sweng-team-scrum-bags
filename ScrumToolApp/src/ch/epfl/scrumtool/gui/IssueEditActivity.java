package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.PlayerListAdapter;
import ch.epfl.scrumtool.gui.util.InputVerifiers;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public class IssueEditActivity extends Activity {

    private EditText issueNameView;
    private EditText issueDescriptionView;
    private EditText issueEstimationView;
    private Player player;
    
    private PlayerListAdapter adapter;
    private Spinner issueAssigneeSpinner;


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

        Project project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        Client.getScrumClient().loadPlayers(project, new DefaultGUICallback<List<Player>>(this) {
            @Override
            public void interactionDone(List<Player> playerList) {
                adapter = new PlayerListAdapter(IssueEditActivity.this, playerList);
                issueAssigneeSpinner.setAdapter(adapter);
            }
        });
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
        issueAssigneeSpinner = (Spinner) findViewById(R.id.issue_assignee_spinner);

        issueNameView.setText(issueBuilder.getName());
        issueDescriptionView.setText(issueBuilder.getDescription());
        issueEstimationView.setText(Float.toString(issueBuilder.getEstimatedTime()));
        if (issueBuilder.getPlayer() == null) {
            issueAssigneeSpinner.setSelection(0);
        } else {
            issueAssigneeSpinner.setSelection(adapter.getList().indexOf(issueBuilder.getPlayer()));
        }
    }

    public void saveIssueChanges(View view) {
        InputVerifiers.updateTextViewAfterValidityCheck(issueNameView, nameIsValid(), getResources());
        InputVerifiers.updateTextViewAfterValidityCheck(issueDescriptionView, descriptionIsValid(), getResources());

        if (nameIsValid() && descriptionIsValid()) {
            String newName = issueNameView.getText().toString();
            String newDescription = issueDescriptionView.getText().toString();
            float newEstimation = Float.parseFloat(issueEstimationView.getText().toString());

            issueBuilder.setName(newName);
            issueBuilder.setDescription(newDescription);
            issueBuilder.setEstimatedTime(newEstimation);
            issueBuilder.setStatus(Status.READY_FOR_ESTIMATION); // TODO get this value from the user
            issueBuilder.setPriority(Priority.NORMAL); // TODO get this value from the user
            issueBuilder.setPlayer((Player) issueAssigneeSpinner.getSelectedItem());

            if (original == null) {
                insertIssue();
            } else {
                updateIssue();
            }
        }
    }

    private void insertIssue() {
        Issue issue = issueBuilder.build();
        issue.insert(parentTask, new DefaultGUICallback<Issue>(this) {
            @Override
            public void interactionDone(Issue issue) {
                IssueEditActivity.this.finish();
            }
        });
    }

    private void updateIssue() {
        Issue issue = issueBuilder.build();
        issue.update(null, parentTask, new DefaultGUICallback<Boolean>(this) {
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

    private boolean nameIsValid() {
        String name = issueNameView.getText().toString();
        return name != null && name.length() > 0
                && name.length() < 50; // TODO put a meaningful value (cyriaque)
    }

    private boolean descriptionIsValid() {
        String description = issueDescriptionView.getText().toString();
        return description != null && description.length() > 0;
    }
}
