package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.components.TaskListAdapter;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class BacklogActivity extends BaseMenuActivity<MainTask> {

	private ListView listView;
	private Project project;

	private TaskListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backlog);

		project = (Project) getIntent().getSerializableExtra(
				Project.SERIALIZABLE_NAME);
		project.loadBacklog(new Callback<List<MainTask>>() {

			@Override
			public void interactionDone(final List<MainTask> taskList) {
				if (taskList == null) {
					Toast.makeText(BacklogActivity.this, "sum sing wong",
							Toast.LENGTH_SHORT).show();
				} else {
					// Get list and initialize adapter
					adapter = new TaskListAdapter(BacklogActivity.this,
							taskList);
					listView = (ListView) findViewById(R.id.backlog_tasklist);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Intent openTaskIntent = new Intent(view
									.getContext(), TaskOverviewActivity.class);
							MainTask task = taskList.get(position);
							openTaskIntent.putExtra(MainTask.SERIALIZABLE_NAME,
									task);
							startActivity(openTaskIntent);
						}
					});

					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		onCreate(null); // TODO right way to do this (cyriaque)
	}

	@Override
	void openEditElementActivity(MainTask task) {
		Intent openTaskEditIntent = new Intent(this, MainTaskEditActivity.class);
		openTaskEditIntent.putExtra(MainTask.SERIALIZABLE_NAME, task);
		openTaskEditIntent.putExtra(Project.SERIALIZABLE_NAME, this.project);
		startActivity(openTaskEditIntent);
	}

	public void addTask(View view) {
		MainTask.Builder tB = new MainTask.Builder();
		tB.setName("Static Task");
		tB.setDescription("I am a super super cool description... YAHOU !");
		tB.setPriority(Priority.HIGH);
		tB.setStatus(Status.READY_FOR_ESTIMATION);
		tB.setId("newTaskId");

		final MainTask newTask = tB.build();

		Client.getScrumClient().insertMainTask(newTask, project,
				new Callback<MainTask>() {

					@Override
					public void interactionDone(MainTask task) {
						if (task != null) {
							Log.d("Task ID", task.getKey());
							// FIXME: need database function
							// project.addTask(task)

							// Intent openTaskOverviewIntent = new
							// Intent(BacklogActivity.this,
							// TaskOverviewActivity.class);
							// openTaskOverviewIntent.putExtra("ch.epfl.scrumtool.TASK",
							// newTask);
							// startActivity(openTaskOverviewIntent);

						} else {
							Log.e("BacklogActivity",
									"Could not insert the new task");
						}
					}
				});
	}
}
