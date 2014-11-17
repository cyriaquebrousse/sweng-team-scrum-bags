package ch.epfl.scrumtool.gui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.SharedProjectAdapter;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 */
public class ProfileOverviewActivity extends BaseOverviewMenuActivity {

    private TextView nameView;
    private TextView jobTitleView;
    private TextView companyNameView;
    private TextView emailView;
    private ListView sharedProjectsListView;
    
    private User userProfile;

    private SharedProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_overview);

        // Get the connected user, and the user to display
        try {
            User userConnected = Session.getCurrentSession().getUser();
            
            if (getIntent().hasExtra(User.SERIALIZABLE_NAME)) {
                userProfile = (User) getIntent().getSerializableExtra(User.SERIALIZABLE_NAME);
            } else {
                userProfile = userConnected;
            }
            
            // Create the adapter
            // TODO : Change empty list by getProjectsSharedWith(userConnected)
            adapter = new SharedProjectAdapter(this, new ArrayList<Project>(), userProfile);

            initViews();

            sharedProjectsListView.setAdapter(adapter);
            sharedProjectsListView
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
                            Toast.makeText(view.getContext(),
                                    "Openning the project #" + position,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            this.finish();
            e.printStackTrace();
        }

    }
    
    public void initViews() {

        // Get Views
        nameView = (TextView) findViewById(R.id.profile_name);
        jobTitleView = (TextView) findViewById(R.id.profile_jobtitle);
        companyNameView = (TextView) findViewById(R.id.profile_company);
        emailView = (TextView) findViewById(R.id.profile_email);
        sharedProjectsListView = (ListView) findViewById(R.id.profile_shared_projects_list);

        // Set Views
        nameView.setText(userProfile.fullname());
        emailView.setText(userProfile.getEmail());
        if (userProfile.getJobTitle().length() > 0) {
            jobTitleView.setText(userProfile.getJobTitle());
        } else {
            findViewById(R.id.profile_field_jobtitle).setVisibility(View.INVISIBLE);
        }
        if (userProfile.getCompanyName().length() > 0) {
            companyNameView.setText(userProfile.getCompanyName());
        } else {
            findViewById(R.id.profile_field_company).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    void openEditElementActivity() {
        Intent intent = new Intent(this, ProfileEditActivity.class);
        startActivity(intent);
    }
}
