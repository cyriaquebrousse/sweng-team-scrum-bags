package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.PlayerListAdapter;
import ch.epfl.scrumtool.gui.components.SprintListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.Stamp;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

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
    private Issue.Builder issueBuilder;
    
    private SprintListAdapter sprintAdapter;
    private PlayerListAdapter playerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_overview);

        issue = (Issue) getIntent().getSerializableExtra(Issue.SERIALIZABLE_NAME);
        parentProject = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        parentTask = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);

        this.setTitle(issue.getName());

        nameView = (TextView) findViewById(R.id.issue_name);
        descriptionView = (TextView) findViewById(R.id.issue_desc);
        statusView = (TextView) findViewById(R.id.issue_status);
        estimationStamp = (Stamp) findViewById(R.id.issue_estimation_stamp);
        assigneeName = (TextView) findViewById(R.id.issue_assignee_name);
        sprintView = (TextView) findViewById(R.id.issue_sprint);

        if (issue.getPlayer() != null) {
            assigneeName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openProfileIntent = new Intent(v.getContext(), ProfileOverviewActivity.class);
                    User assignee = issue.getPlayer().getUser();
                    openProfileIntent.putExtra(User.SERIALIZABLE_NAME, assignee);
                    startActivity(openProfileIntent);
                }
            });
        }

        initializeListeners();

        updateViews();
    }

    private void updateViews() {
        nameView.setText(issue.getName());
        descriptionView.setText(issue.getDescription());
        statusView.setText(issue.getStatus().toString());

        if (issue.getPlayer() != null) {
            assigneeName.setText(issue.getPlayer().getUser().getName());
            assigneeName.setPaintFlags(assigneeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            assigneeName.setText("No player assigned");
        }
        if (issue.getSprint() != null) {
            sprintView.setText(issue.getSprint().getTitle());
        } else {
            sprintView.setText("No sprint assigned");
        }

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
        new AlertDialog.Builder(this).setTitle("Delete Issue")
        .setMessage("Do you really want to delete this issue?")
        .setIcon(R.drawable.ic_dialog_alert)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Context context = IssueOverviewActivity.this;
                issue.remove(new DefaultGUICallback<Boolean>(context) {
                    @Override
                    public void interactionDone(Boolean success) {
                        Toast.makeText(context, "Issue deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        })
        .setNegativeButton(android.R.string.no, null).show();
    }

    private void initializeListeners() {

        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(IssueOverviewActivity.this, new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        issueBuilder = new Issue.Builder(issue);
                        issueBuilder.setName(userInput);
                        nameView.setText(userInput);
                        updateIssue();
                    }
                });
            }
        });

        descriptionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(IssueOverviewActivity.this, new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        issueBuilder = new Issue.Builder(issue);
                        issueBuilder.setDescription(userInput);
                        descriptionView.setText(userInput);
                        updateIssue();
                    }
                });
            }
        });

        statusView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.showStatusEditDialog(IssueOverviewActivity.this, new DialogCallback<Status>() {
                    @Override
                    public void onSelected(Status selected) {
                        issueBuilder = new Issue.Builder(issue);
                        issueBuilder.setStatus(selected);
                        statusView.setText(selected.toString());
                        updateIssue();
                    }
                });
            }
        });
        
        
        sprintView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSprintSelector(IssueOverviewActivity.this, new DialogCallback<Sprint>() {
                    @Override
                    public void onSelected(Sprint selected) {
                        issueBuilder = new Issue.Builder(issue);
                        issueBuilder.setSprint(selected);
                        sprintView.setText(selected.getTitle().toString());
                        updateIssue();
                    }
                });
            }
        });


    }

    private void updateIssue() {
        issueBuilder.build().update(null, new DefaultGUICallback<Boolean>(IssueOverviewActivity.this) {
            @Override
            public void interactionDone(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(IssueOverviewActivity.this, 
                            "Could not update issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    public void showPlayerSelector(final Activity parent, final DialogCallback<Player> callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        
        builder.setTitle("Set Player");
        Client.getScrumClient().loadPlayers(parentProject,new DefaultGUICallback<List<Player>>(this) {
            @Override
            public void interactionDone(final List<Player> playerList) {
                playerAdapter = new PlayerListAdapter(parent, playerList);
            }
        });
        
        builder.setAdapter(playerAdapter, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onSelected(playerAdapter.getItem(which));
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
    public void showSprintSelector(final Activity parent, final DialogCallback<Sprint> callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        
        builder.setTitle("Set Sprint");
        Client.getScrumClient().loadSprints(parentProject, new DefaultGUICallback<List<Sprint>>(parent) {
            @Override
            public void interactionDone(final List<Sprint> sprintList) {
                sprintAdapter = new SprintListAdapter(parent, sprintList);
            }
        });
        
        builder.setAdapter(sprintAdapter, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onSelected(sprintAdapter.getItem(which));
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
}
