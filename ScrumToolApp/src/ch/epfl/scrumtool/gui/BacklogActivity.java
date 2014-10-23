package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.network.BogusTask;

/**
 * @author cyriaquebrousse
 */
public class BacklogActivity extends Activity {

    private DummyBacklog backlog = new DummyBacklog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        String[] tasksAsStrings = new String[backlog.size()];
        for (int i = 0; i < tasksAsStrings.length; i++) {
            tasksAsStrings[i] = "Task #" + i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.backlog_tasklist_row, R.id.backlog_tasklist_row_label,
                tasksAsStrings);
        ListView tasklistView = (ListView) findViewById(R.id.backlog_tasklist);
        tasklistView.setAdapter(adapter);
        tasklistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "Task "+ position +" selected", Toast.LENGTH_SHORT).show();
                Intent startTaskActivityIntent = new Intent(view.getContext(), TaskOverviewActivity.class);
                startActivity(startTaskActivityIntent);
            }
        });
    }

    /**
     * TODO should be deleted once the real backlog class has been created and
     * implemented
     */
    private class DummyBacklog {
        private List<BogusTask> tasks = new ArrayList<>();

        /**
         * For demonstration purposes, there are 20 dummy tasks in the dummy
         * backlog. They do not have any description
         */
        public DummyBacklog() {
            for (int i = 0; i < 20; i++) {
                tasks.add(new BogusTask());
            }
        }

        public int size() {
            return tasks.size();
        }
    }
}
