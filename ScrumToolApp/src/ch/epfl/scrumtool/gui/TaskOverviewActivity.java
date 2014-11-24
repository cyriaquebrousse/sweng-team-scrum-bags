package ch.epfl.scrumtool.gui;

import java.util.List;

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
import ch.epfl.scrumtool.gui.components.IssueListAdapter;
import ch.epfl.scrumtool.gui.components.widgets.PrioritySticker;
import ch.epfl.scrumtool.gui.components.widgets.Slate;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;
import ch.epfl.scrumtool.util.gui.TextViewModifiers;
import ch.epfl.scrumtool.util.gui.TextViewModifiers.PopupCallback;

/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public class TaskOverviewActivity extends BaseListMenuActivity<Issue> implements OnMenuItemClickListener {

    private TextView nameView;
    private TextView descriptionView;
    private PrioritySticker prioritySticker;
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

        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_issue_list);
        onCreateSwipeToRefresh(listViewLayout);
        emptyViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_empty_issue_list);
        onCreateSwipeToRefresh(emptyViewLayout);

        emptyViewLayout.setVisibility(View.INVISIBLE);

        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        task = (MainTask) getIntent().getSerializableExtra(MainTask.SERIALIZABLE_NAME);

        this.setTitle(task.getName());

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        task.loadIssues(callback);
        updateViews();
    }
    
    private void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                task.loadIssues(callback);
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void initViews() {
        nameView = (TextView) findViewById(R.id.task_name);
        descriptionView = (TextView) findViewById(R.id.task_desc);
        prioritySticker = (PrioritySticker) findViewById(R.id.task_priority);
        statusSlate = (Slate) findViewById(R.id.task_slate_status);
        estimationSlate = (Slate) findViewById(R.id.task_slate_estimation);
        listView = (ListView) findViewById(R.id.issue_list);

        nameView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewModifiers.modifyText(TaskOverviewActivity.this, "name",
                        nameView.getText().toString(), new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        taskBuilder = new MainTask.Builder(task);
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
                TextViewModifiers.modifyText(TaskOverviewActivity.this, "description",
                        descriptionView.getText().toString(), new PopupCallback() {
                    @Override
                    public void onModified(String userInput) {
                        taskBuilder = new MainTask.Builder(task);
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
                Dialogs.showTaskPriorityEditDialog(TaskOverviewActivity.this, new DialogCallback<Priority>() {
                    @Override
                    public void onSelected(Priority selected) {
                        taskBuilder = new MainTask.Builder(task);
                        taskBuilder.setPriority(selected);
                        prioritySticker.setPriority(selected);
                        updateTask();
                    }
                });
            }
        });

        statusSlate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.showStatusEditDialog(TaskOverviewActivity.this, new DialogCallback<Status>() {
                    @Override
                    public void onSelected(Status selected) {
                        taskBuilder = new MainTask.Builder(task);
                        taskBuilder.setStatus(selected);
                        statusSlate.setText(selected.toString());
                        updateTask();
                    }
                });
            }
        });
    }

    private void updateViews() {
        nameView.setText(task.getName());
        descriptionView.setText(task.getDescription());
        prioritySticker.setPriority(task.getPriority());
        statusSlate.setText(task.getStatus().toString());
        
        task.loadIssues(new DefaultGUICallback<List<Issue>>(this) {
            @Override
            public void interactionDone(List<Issue> object) {
                float estimatedTime = task.estimatedTime(object);
                // FIXME not hours!
                estimationSlate.setText(estimatedTime < estimatedTime ? "?" : Float.toString(estimatedTime) + " hours");
            }
        });
        
        
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_entity_edit:
                openEditElementActivity(adapter.getItem(info.position));
                return true;
            case R.id.action_entity_delete:
                deleteIssue(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * @param project
     *            the project to delete
     */
    private void deleteIssue(final Issue issue) {
        listViewLayout.setRefreshing(true);
        issue.remove(new DefaultGUICallback<Boolean>(this) {
            @Override
            public void interactionDone(Boolean success) {
                listViewLayout.setRefreshing(false);
                adapter.remove(issue);
            }
        });
    }

    @Override
    void openEditElementActivity(Issue issue) {
        Intent openIssueEditIntent = new Intent(this, IssueEditActivity.class);
        openIssueEditIntent.putExtra(Issue.SERIALIZABLE_NAME, issue);
        openIssueEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, this.task);
        openIssueEditIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openIssueEditIntent);
    }

    private void updateTask() {
        task = taskBuilder.build();
        task.update(null, new DefaultGUICallback<Boolean>(TaskOverviewActivity.this) {
            @Override
            public void interactionDone(Boolean success) {
                if (!success.booleanValue()) {
                    Toast.makeText(TaskOverviewActivity.this, 
                            "Could not update task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
