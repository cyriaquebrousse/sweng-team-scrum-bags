package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.verifyDescriptionIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyEstimationIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyNameIsValid;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.PlayerListAdapter;
import ch.epfl.scrumtool.gui.components.adapters.SprintListAdapter;


/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public class IssueEditActivity extends BaseEditMenuActivity {

    private EditText issueNameView;
    private EditText issueDescriptionView;
    private EditText issueEstimationView;

    private PlayerListAdapter playerAdapter;
    private SprintListAdapter sprintAdapter;
    private Spinner issueAssigneeSpinner;
    private Spinner sprintSpinner;


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
        throwIfNull("Parent project cannot be null", project);
        View next = findViewById(Menu.FIRST);
        project.loadPlayers(new DefaultGUICallback<List<Player>>(this, next) {
            @Override
            public void interactionDone(List<Player> playerList) {
                List<String> playerKeyList = new ArrayList<String>();
                for (Player player : playerList) {
                    playerKeyList.add(player.getKey());
                }
                playerList.add(0, null);
                playerAdapter = new PlayerListAdapter(IssueEditActivity.this, playerList);
                issueAssigneeSpinner.setAdapter(playerAdapter);

                if (issueBuilder.getPlayer() == null) {
                    issueAssigneeSpinner.setSelection(0);
                } else {
                    issueAssigneeSpinner.setSelection(
                            playerKeyList.indexOf(((Player) issueBuilder.getPlayer()).getKey()) + 1);
                }
            }
        });

        project.loadSprints(new DefaultGUICallback<List<Sprint>>(this, next) {
            @Override
            public void interactionDone(List<Sprint> sprintList) {
                List<String> sprintKeyList = new ArrayList<String>();
                for (Sprint sprint : sprintList) {
                    sprintKeyList.add(sprint.getKey());
                }
                sprintList.add(0, null);
                sprintAdapter = new SprintListAdapter(IssueEditActivity.this, sprintList);
                sprintSpinner.setAdapter(sprintAdapter);

                if (issueBuilder.getSprint() == null) {
                    sprintSpinner.setSelection(0);
                } else {
                    sprintSpinner.setSelection(
                            sprintKeyList.indexOf(((Sprint) issueBuilder.getSprint()).getKey()) + 1);
                }
            }
        });
    }

    @Override
    protected void saveElement() {
        saveIssueChanges();
    }

    private void initOriginalAndParentTask() {
        original = (Issue) getIntent().getSerializableExtra(Issue.SERIALIZABLE_NAME);
        parentTask = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);
        if (original == null) {
            issueBuilder = new Issue.Builder();
            setTitle(R.string.title_activity_issue_edit_new);
            throwIfNull("Parent task cannot be null", parentTask);
        } else {
            issueBuilder = new Issue.Builder(original);
        }
    }

    private void initViews() {
        issueNameView = (EditText) findViewById(R.id.issue_name_edit);
        issueDescriptionView = (EditText) findViewById(R.id.issue_description_edit);
        issueEstimationView = (EditText) findViewById(R.id.issue_estimation_edit);
        issueAssigneeSpinner = (Spinner) findViewById(R.id.issue_assignee_spinner);
        sprintSpinner = (Spinner) findViewById(R.id.issue_sprint_spinner);

        setTitle(issueBuilder.getName());

        issueNameView.setText(issueBuilder.getName());
        issueDescriptionView.setText(issueBuilder.getDescription());
        issueEstimationView.setText(Float.toString(issueBuilder.getEstimatedTime()));

        OnFocusChangeListener listener = new OnFocusChangeListener() {
            
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && issueEstimationView.getText().toString().equals("0.0")) {
                    issueEstimationView.setText("");
                    issueEstimationView.setOnFocusChangeListener(null);
                }
            }
        };
        issueEstimationView.setOnFocusChangeListener(listener);
    }

    private void saveIssueChanges() {
        Resources resources = getResources();
        boolean nameIsValid = verifyNameIsValid(issueNameView, resources);
        boolean descriptionIsValid = verifyDescriptionIsValid(issueDescriptionView, resources);
        boolean estimationIsValid = verifyEstimationIsValid(issueEstimationView, resources);

        if (nameIsValid && descriptionIsValid && estimationIsValid) {
            findViewById(Menu.FIRST).setEnabled(false);
            String newName = issueNameView.getText().toString();
            String newDescription = issueDescriptionView.getText().toString();
            float newEstimation = Float.parseFloat(issueEstimationView.getText().toString());

            setTitle(newName);

            issueBuilder.setName(newName);
            issueBuilder.setDescription(newDescription);
            issueBuilder.setEstimatedTime(newEstimation);
            issueBuilder.setStatus(Status.READY_FOR_ESTIMATION); // TODO status is now handled by server
            issueBuilder.setPriority(Priority.NORMAL); // TODO for now issue priorities are useless
            issueBuilder.setPlayer((Player) issueAssigneeSpinner.getSelectedItem());
            issueBuilder.setSprint((Sprint) sprintSpinner.getSelectedItem());

            if (original == null) {
                insertIssue();
            } else {
                updateIssue();
            }
        }
    }

    private void insertIssue() {
        Issue issue = issueBuilder.build();
        View next = findViewById(Menu.FIRST);
        issue.insert(parentTask, new DefaultGUICallback<Issue>(this, next) {
            @Override
            public void interactionDone(Issue issue) {
                IssueEditActivity.this.finish();
            }
        });
    }

    private void updateIssue() {
        final Issue issue = issueBuilder.build();
        View next = findViewById(Menu.FIRST);
        issue.update(new DefaultGUICallback<Void>(this, next) {
            @Override
            public void interactionDone(Void success) {
                passResult(issue);
                IssueEditActivity.this.finish();
            }
        });
    }

    private void passResult(Issue issue) {
        Intent intent = new Intent();
        intent.putExtra(Issue.SERIALIZABLE_NAME, issue);
        setResult(RESULT_OK, intent);
    }
}
