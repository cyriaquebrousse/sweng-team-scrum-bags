package ch.epfl.scrumtool.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.components.SharedProjectAdapter;
import ch.epfl.scrumtool.network.ServerSimulator;

/**
 * @author ketsio
 */
public class ProfileOverviewActivity extends Activity {

    private TextView nameView;
    private TextView usernameView;
    private TextView emailView;
    private ListView sharedProjectsListView;

    private SharedProjectAdapter adapter;
    private User userConnected;
    private User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_overview);

        // Get the connected user, and the user to display
        this.userConnected = Entity.CONNECTED_USER;
        long userId = getIntent().getLongExtra("ch.epfl.scrumtool.USER_ID", 0);
        userProfile = ServerSimulator.getUserById(userId);
        
        // Create the adapter
        adapter = null; //new SharedProjectAdapter(this, userProfile.getProjectsSharedWith(userConnected), userProfile);

        // Get Views
        nameView = (TextView) findViewById(R.id.profile_name);
        usernameView = (TextView) findViewById(R.id.profile_username);
        emailView = (TextView) findViewById(R.id.profile_email);
        sharedProjectsListView = (ListView) findViewById(R.id.profile_shared_projects_list);

        // Set Views
        nameView.setText(userProfile.getName());
        emailView.setText(userProfile.getEmail());

        sharedProjectsListView.setAdapter(adapter);
        sharedProjectsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(),
                        "Openning the project #" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
