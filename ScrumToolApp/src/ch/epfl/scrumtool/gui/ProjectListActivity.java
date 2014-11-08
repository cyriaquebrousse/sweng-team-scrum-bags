package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.ProjectListAdapter;
import ch.epfl.scrumtool.network.Session;

/**
 * @author Cyriaque Brousse
 */
public class ProjectListActivity extends Activity implements
        OnMenuItemClickListener {

    private ListView listView;
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

        // Create some dummy projects and add them to the list
        try {
            Session.getCurrentSession().getUser().loadProjects(new Callback<List<Project>>() {
                @Override
                public void interactionDone(
                        final List<Project> projectList) {

                    // Get list and initialize its adapter
                    adapter = new ProjectListAdapter(
                            ProjectListActivity.this, projectList);
                    listView = (ListView) findViewById(R.id.project_list);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
                            Intent openProjectOverviewIntent = new Intent(
                                    view.getContext(),
                                    ProjectOverviewActivity.class);

                            // Pass the project Id
                            Project project = projectList.get(position);
                            openProjectOverviewIntent.putExtra(
                                    "ch.epfl.scrumtool.PROJECT",
                                    project);

                            startActivity(openProjectOverviewIntent);
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
            });
        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_projectlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_project_new:
                openProjectEditActivity(null);
                return true;
            case R.id.action_overflow:
                View v = (View) findViewById(R.id.action_overflow);
                showPopup(v);
                return true;
            case R.id.action_logout:
                Log.d("ProjectListActivity", "Lol, it works");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                openLoginActivity();
                return true;
            default:
                return false;
        }
    }

    /**
     * @param project
     *            if {@code null}, the edit activity will behave so that the
     *            user can create a project
     */
    private void openProjectEditActivity(Project project) {
        Intent openProjectEditIntent = new Intent(this,
                ProjectEditActivity.class);
        openProjectEditIntent.putExtra("ch.epfl.scrumtool.PROJECT", project);
        startActivity(openProjectEditIntent);
    }

    private void openLoginActivity() {
        Session.destroyCurrentSession(this);
        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(openLoginIntent);
    }
}
