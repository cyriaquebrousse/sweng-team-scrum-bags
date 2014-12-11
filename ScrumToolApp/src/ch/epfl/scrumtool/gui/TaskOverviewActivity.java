package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.entity.Status.FINISHED;
import static ch.epfl.scrumtool.entity.Status.IN_SPRINT;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_ESTIMATION;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.gui.components.widgets.Slate;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;
import ch.epfl.scrumtool.util.gui.EstimationFormating;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.FieldType;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public class TaskOverviewActivity extends BaseListMenuActivity<Issue> implements OnMenuItemClickListener {

    private TextView nameView;
    private TextView descriptionView;
    private PrioritySticker prioritySticker;
    private View priorityBar;
    private Slate statusSlate;
    private Slate estimationSlate;
    private ListView listView;
    private SwipeRefreshLayout listViewLayout;
    private SwipeRefreshLayout emptyViewLayout;

    private MainTask task;
    private IssueListAdapter adapter;
    private Project project;
    private MainTask.Builder taskBuilder;

    private Callback<List<Issue>> callback = new DefaultGUICallback<List<Issue>>(this) {
        @Override
        public void interactionDone(final List<Issue> issueList) {
            listViewLayout.setRefreshing(false);
            emptyViewLayout.setRefreshing(false);

            adapter = new IssueListAdapter(TaskOverviewActivity.this, issueList);
            listView.setEmptyView(emptyViewLayout);
            listView.setAdapter(adapter);

            if (!issueList.isEmpty()) {
                registerForContextMenu(listView);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent openIssueIntent = new Intent(view.getContext(), IssueOverviewActivity.class);
                        Issue issue = issueList.get(position);
                        openIssueIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
                        openIssueIntent.putExtra(Project.SERIALIZABLE_NAME, project);
                        openIssueIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
                        startActivity(openIssueIntent);
                    }
                });
            } else {
                emptyViewLayout.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        task = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);
        throwIfNull("Parent object cannot be null", project, task);

        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_issue_list);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_issue_list);
        onCreateSwipeToRefresh(emptyViewLayout);

        emptyViewLayout.setVisibility(View.INVISIBLE);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        task.loadIssues(callback);
        updateViews();
    }

    private void initViews() {
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (PrioritySticker) findViewById(R.id.task_priority);
        priorityBar = (View) findViewById(R.id.task_priority_bar);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
        listView = (ListView) findViewById(R.id.issue_list);
    
        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(TaskOverviewActivity.this,
                        FieldType.NAMEFIELD, nameView.getText().toString(),
                        new PopupCallback<String>() {
                            @Override
                            public void onModified(String userInput) {
                                taskBuilder = task.getBuilder();
                                taskBuilder.setName(userInput);
                                nameView.setText(userInput);
                                updateTask();
                            }
                        });
            }
        });
    
        descriptionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(TaskOverviewActivity.this,
                        FieldType.DESCRIPTIONFIELD, descriptionView.getText().toString(),
                        new PopupCallback<String>() {
                            @Override
                            public void onModified(String userInput) {
                                taskBuilder = task.getBuilder();
                                taskBuilder.setDescription(userInput);
                                descriptionView.setText(userInput);
                                updateTask();
                            }
                        });
            }
        });
    
        prioritySticker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showTaskPriorityEditDialog(TaskOverviewActivity.this,
                    new DialogCallback<Priority>() {
                        @Override
                        public void onSelected(Priority selected) {
                            taskBuilder = task.getBuilder();
                            taskBuilder.setPriority(selected);
                            prioritySticker.setPriority(selected);
                            priorityBar.setBackgroundColor(getResources().getColor(selected.getColorRef()));
                            updateTask();
                        }
                    });
            }
        });
    }

    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                task.loadIssues(callback);
                refreshLayout.setRefreshing(false);
                updateViews();
            }
        });
    }

    /**
     * Updates the views reflecting the task's internals. This method calls
     * {@link #updateTaskAndBuilderAccordingToNewStatusAndEstimation(String)},
     * which also updates the estimation and status views. The latter operation
     * cannot be performed directly here in updateViews, since computing these
     * two new values requires a network callback.
     */
    private void updateViews() {
        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        prioritySticker.setPriority(task.getPriority());
        priorityBar.setBackgroundColor(getResources().getColor(task.getPriority().getColorRef()));
        updateTaskAndBuilderAccordingToNewStatusAndEstimation();
    }

    /**
     * Updates the estimation slate with the new estimation for the task
     * 
     * @param estimatedTime
     *            the new estimation
     */
    private void updateEstimationSlateInfo(final float estimatedTime) {
        String unit = getResources().getString(R.string.project_default_unit);
        if (Float.compare(estimatedTime, 1.0f) <= 0) {
            unit = getResources().getString(R.string.project_single_unit);
        }
        estimationSlate.setText(estimatedTime <= 0 ? "―" : EstimationFormating
                .estimationAsHourFormat(estimatedTime) + " " + unit);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_markable_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final Issue issue = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.action_entity_edit:
                openEditElementActivity(issue);
                return true;
            case R.id.action_entity_delete:
                deleteIssue(issue);
                return true;
            case R.id.action_entity_markAsDoneUndone:
                markIssueAsDone(issue, issue.getStatus() != Status.FINISHED);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Deletes an issue from the task
     * 
     * @param issue
     *            the issue to be deleted
     */
    private void deleteIssue(final Issue issue) {
        listViewLayout.setRefreshing(true);
        new AlertDialog.Builder(this).setTitle("Delete Issue")
            .setMessage("Do you really want to delete this Issue? "
                    + "This will remove the Issue and its links with Players and Sprints.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = TaskOverviewActivity.this;
                    issue.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            Toast.makeText(context , "Issue deleted", Toast.LENGTH_SHORT).show();
                            listViewLayout.setRefreshing(false);
                            adapter.remove(issue);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(false);
                }
            }).show();
    }

    @Override
    void openEditElementActivity(Issue issue) {
        Intent openIssueEditIntent = new Intent(this, IssueEditActivity.class);
        openIssueEditIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
        openIssueEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, this.task);
        openIssueEditIntent.putExtra(Project.SERIALIZABLE_NAME, this.project);
        startActivity(openIssueEditIntent);
    }

    private void updateTask() {
        task = taskBuilder.build();
        task.update(new DefaultGUICallback<Void>(TaskOverviewActivity.this) {
            @Override
            public void interactionDone(Void v) { }
        });
    }

    /**
     * @param issue
     *            the issue to update
     * @param done
     *            true if need to update to done, false for undone
     */
    private void markIssueAsDone(final Issue issue, boolean done) {
        issue.markAsDone(done, new Callback<Void>() {
            @Override
            public void interactionDone(Void v) {
                listViewLayout.setRefreshing(true);
                task.loadIssues(callback);
                updateViews();
            }

            @Override
            public void failure(String errorMessage) {
                Toast.makeText(TaskOverviewActivity.this, "Could not mark as done/undone", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Modifies {@link #task} and {@link #taskBuilder} according to changes in
     * the task's issue list.<br>
     * It also updates the status and estimation views accordingly.
     */
    private void updateTaskAndBuilderAccordingToNewStatusAndEstimation() {
        task.loadIssues(new Callback<List<Issue>>() {
            @Override
            public void interactionDone(final List<Issue> issues) {
                final Set<Issue> issueSet = new HashSet<Issue>(issues);

                // Predict new status and estimation according to updated issue set
                final Status status = simulateNewStatus(issueSet);
                final float estimation = simulateNewEstimation(issueSet);
                
                // Update task and builder
                taskBuilder = task.getBuilder()
                        .setStatus(status)
                        .setTotalIssues(issueSet.size())
                        .setTotalIssueTime(timeTotal(issueSet))
                        .setFinishedIssues(countIssuesForStatus(issueSet, FINISHED))
                        .setFinishedIssueTime(timeFinished(issueSet));
                task = taskBuilder.build();
                
                // Update status and estimation views
                statusSlate.setText(status.toString());
                updateEstimationSlateInfo(estimation);
            }

            @Override
            public void failure(String errorMessage) { }
            
            
            /* ****************************************************************
             * Below are a collection of utility methods concerning issues.
             * 
             * Some are just copied from the server project, other are
             * auxiliaries we need here (and nowhere else, that's why they were
             * not made publicly and statically accessible in the .util package.
             * ****************************************************************
             */

            /**
             * Simulates the new task status. It does not have any side effects
             * (e.g. server modifications, modifications on members, etc.). <br>
             * See {@code ScrumMainTask#verifyAndSetStatusWithRespectToIssues}
             * in the app engine project. The logic is implemented and detailed
             * there. Here is just a duplication.
             * 
             * @return the simulated status
             */
            private Status simulateNewStatus(final Set<Issue> allIssues) {
                if (allIssues == null || allIssues.isEmpty()) {
                    return READY_FOR_ESTIMATION;
                }

                final Set<Issue> issues = new HashSet<Issue>(allIssues);

                if (allIssuesHaveStatus(issues, FINISHED)) {
                    return FINISHED;
                }

                issues.removeAll(allIssuesWithStatus(issues, FINISHED));

                if (allIssuesHaveStatus(issues, READY_FOR_SPRINT)) {
                    return READY_FOR_SPRINT;
                }

                final Set<Issue> allInSprintIssues = allIssuesWithStatus(issues, IN_SPRINT);
                final Set<Issue> notInSprintIssues = new HashSet<Issue>(issues);
                notInSprintIssues.removeAll(allInSprintIssues);
                if (!allInSprintIssues.isEmpty() && allIssuesHaveStatus(notInSprintIssues, READY_FOR_SPRINT)) {
                    return IN_SPRINT;
                } else {
                    return READY_FOR_ESTIMATION;
                }
            }

            private int countIssuesForStatus(final Set<Issue> issueSet, Status status) {
                int count = 0;
                for (Issue i : issueSet) {
                    if (i.getStatus() == status) {
                        ++count;
                    }
                }
                return count;
            }

            private boolean allIssuesHaveStatus(Set<Issue> issues, Status status) {
                for (Issue i : issues) {
                    if (i.getStatus() != status) {
                        return false;
                    }
                }
                return true;
            }

            private Set<Issue> allIssuesWithStatus(Set<Issue> issues, Status status) {
                Set<Issue> allIssuesWithStatus = new HashSet<Issue>();
                for (Issue i : issues) {
                    if (i.getStatus() == status) {
                        allIssuesWithStatus.add(i);
                    }
                }
                return allIssuesWithStatus;
            }
            
            /**
             * Simulates the new task estimated time. It does not have any side
             * effects (e.g. server modifications, modifications on members,
             * etc.). <br>
             * See {@code ScrumMainTaskEndpoint#computeMainTaskIssueInfo} in the
             * app engine project. The logic is implemented and detailed there.
             * Here is just a duplication.
             * 
             * @return the simulated estimated time
             */
            private float simulateNewEstimation(final Set<Issue> issueSet) {
                float estimatedTime = 0f;
                for (Issue i : issueSet) {
                    if (i.getStatus() != FINISHED) {
                        estimatedTime += i.getEstimatedTime();
                    }
                }

                return estimatedTime;
            }
            
            private float timeTotal(final Set<Issue> issueSet) {
                float time = 0f;
                for (Issue i : issueSet) {
                    time += i.getEstimatedTime();
                }
                return time;
            }
            
            private float timeFinished(final Set<Issue> issueSet) {
                float time = 0f;
                for (Issue i : issueSet) {
                    if (i.getStatus() == FINISHED) {
                        time += i.getEstimatedTime();
                    }
                }
                return time;
            }

        });
    }
}
