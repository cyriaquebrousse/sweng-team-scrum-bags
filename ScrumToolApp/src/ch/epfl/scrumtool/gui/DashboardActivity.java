package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.Session;

/**
 * @author ketsio
 * @author Cyriaque Brousse
 */
public class DashboardActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        try {
            Session.getCurrentSession().getUser().loadIssuesForUser(new Callback<List<Issue>>() {
                @Override
                public void interactionDone(List<Issue> issues) {
                    // TODO Auto-generated method stub
                }
                
                @Override
                public void failure(String errorMessage) {
                    // TODO Auto-generated method stub
                }
            });
        } catch (NotAuthenticatedException e) {
            Session.relogin(this);
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
