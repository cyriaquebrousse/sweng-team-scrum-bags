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
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.UserInterface;
import ch.epfl.scrumtool.gui.components.SharedProjectAdapter;

/**
 * 
 * @author ketsio
 *
 */
public class ProfileOverviewActivity extends Activity {

    private TextView nameView;
    private TextView usernameView;
    private TextView emailView;
    private ListView sharedProjectsListView;

    private SharedProjectAdapter adapter;
    private UserInterface userConnected;
    private UserInterface userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_overview);

        // Get the users
        this.userConnected = getConnectedUser();
        this.userProfile = getUserFromPreviousActivity();

        // Create the adapter
        adapter = new SharedProjectAdapter(this,
                userProfile.getProjectsSharedWith(userConnected), userProfile);

        // Get Views
        nameView = (TextView) findViewById(R.id.profile_name);
        usernameView = (TextView) findViewById(R.id.profile_username);
        emailView = (TextView) findViewById(R.id.profile_email);
        sharedProjectsListView = (ListView) findViewById(R.id.profile_shared_projects_list);

        // Set Views
        nameView.setText(userProfile.getName());
        usernameView.setText(userProfile.getUsername());
        emailView.setText(userProfile.getEmail());

        sharedProjectsListView.setAdapter(adapter);
        sharedProjectsListView
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                        Toast.makeText(view.getContext(),
                                "Openning the project #" + position,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Deprecated
    private UserInterface getConnectedUser() {
        return new User(1, "Maria Linda", "@maria_linda", "maria_linda@gmail.com",
                null);
    }

    @Deprecated
    private UserInterface getUserFromPreviousActivity() {
        return new User(1, "John Smith", "@john_smith",
                "john.smith@gmail.com", null);
    }
}
