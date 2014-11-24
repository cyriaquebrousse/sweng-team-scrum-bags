package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 */
public class DashboardActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.setTitle("Dashboard");
        try {
            Client.getScrumClient().loadIssuesForUser(Session.getCurrentSession().getUser(), new Callback<List<Issue>>() {
                
                @Override
                public void interactionDone(List<Issue> object) {
                    object.hashCode();
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void failure(String errorMessage) {
                    // TODO Auto-generated method stub
                    
                }
            });
        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openProjectList(View view) {
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
    }
    
    public void openMyProfile(View view) {
        Intent intent = new Intent(this, ProfileOverviewActivity.class);
        startActivity(intent);
    }
}
