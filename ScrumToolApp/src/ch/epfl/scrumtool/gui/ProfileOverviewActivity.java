package ch.epfl.scrumtool.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.SharedProjectAdapter;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 */
public class ProfileOverviewActivity extends BaseOverviewMenuActivity {

    private TextView nameView;
    private TextView jobTitleView;
    private TextView companyNameView;
    private TextView dateOfBirthView;
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
            if (getIntent().hasExtra(User.SERIALIZABLE_NAME)) {
                userProfile = (User) getIntent().getSerializableExtra(User.SERIALIZABLE_NAME);
            } else {
                userProfile = Session.getCurrentSession().getUser();
            }
            this.setTitle(userProfile.getName());
            
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
        dateOfBirthView = (TextView) findViewById(R.id.profile_date_of_birth);
        emailView = (TextView) findViewById(R.id.profile_email);
        sharedProjectsListView = (ListView) findViewById(R.id.profile_shared_projects_list);

        // Set Views
        nameView.setText(userProfile.fullname());
        emailView.setText(userProfile.getEmail());
        if (userProfile.getJobTitle().length() > 0) {
            jobTitleView.setText(userProfile.getJobTitle());
        } else {
            findViewById(R.id.profile_field_jobtitle).setVisibility(View.GONE);
        }
        if (userProfile.getCompanyName().length() > 0) {
            companyNameView.setText(userProfile.getCompanyName());
        } else {
            findViewById(R.id.profile_field_company).setVisibility(View.GONE);
        }
        if (userProfile.getDateOfBirth() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(getResources()
                    .getString(R.string.format_date), Locale.ENGLISH);
            dateOfBirthView.setText(sdf.format(userProfile.getDateOfBirth()));
        } else {
            findViewById(R.id.profile_field_date_of_birth).setVisibility(View.GONE);
        }
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null); // TODO right way to do it? (loris)
    }

    @Override
    void openEditElementActivity() {
        Intent intent = new Intent(this, ProfileEditActivity.class);
        startActivity(intent);
    }

    @Override
    void deleteElement() {
        new AlertDialog.Builder(this).setTitle("Delete User")
            .setMessage("Do you really want to delete this User? "
                    + "This will remove your account and you will lose your access to projects.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = ProfileOverviewActivity.this;
                    userProfile.remove(new DefaultGUICallback<Boolean>(context) {
                        @Override
                        public void interactionDone(Boolean success) {
                            Toast.makeText(context , "User deleted", Toast.LENGTH_SHORT).show();
                            GoogleSession.destroyCurrentSession(context);
                        }
                    });
                    finish();
                }
            })
            .setNegativeButton(android.R.string.no, null).show();
    }
}
