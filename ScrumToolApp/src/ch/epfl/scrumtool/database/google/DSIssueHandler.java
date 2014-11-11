package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.IssueHandler;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSIssueHandler implements IssueHandler {
    private ScrumIssue scrumIssue;

    @Override
    public void insert(final Issue issue, final Callback<Issue> callback) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void insert(final Issue issue, final MainTask maintask, final Callback<Issue> callback) {
        
        scrumIssue = new ScrumIssue();
        scrumIssue.setName(issue.getName());
        scrumIssue.setDescription(issue.getDescription());
        scrumIssue.setStatus(issue.getStatus().name());
        scrumIssue.setEstimation(issue.getEstimatedTime());
        
        ScrumPlayer scrumPlayer = new ScrumPlayer();
        scrumPlayer.setKey(issue.getPlayer().getId());
        scrumPlayer.setAdminFlag(false);
        scrumPlayer.setRole(issue.getPlayer().getRole().name());
        
        Date date = new Date();
        scrumIssue.setLastModDate(date.getTime());
        scrumPlayer.setLastModDate(date.getTime());
        try {
            scrumIssue.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
            scrumPlayer.setLastModUser(Session.getCurrentSession().getUser().getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }
        
        scrumIssue.setAssignedPlayer(scrumPlayer);
        
        AsyncTask<ScrumIssue, Void, OperationStatus> task = new AsyncTask<ScrumIssue, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumIssue... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertScrumIssue(maintask.getKey(), params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO : redirecting to the login activity if not connected
                    e.printStackTrace();
                }
                return opStat;
            }
            @Override
            protected void onPostExecute(OperationStatus opStat) {
                Issue.Builder issueBuilder = new Issue.Builder(issue);
                issueBuilder.setId(opStat.getKey());
                callback.interactionDone(issueBuilder.build());
            }
        };
        task.execute(scrumIssue);
    }
    
    
    @Override
    public void load(final String issueKey, final Callback<Issue> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Issue modified, final Issue ref , final Callback<Boolean> callback) {
        final ScrumIssue changes = new ScrumIssue();
        changes.setKey(modified.getKey());
        changes.setName(modified.getName());
        changes.setDescription(modified.getDescription());
        changes.setStatus(modified.getStatus().name());
        changes.setEstimation(modified.getEstimatedTime());
        Date date = new Date();
        changes.setLastModDate(date.getTime());
        try {
            changes.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }
        AsyncTask<ScrumIssue, Void, OperationStatus> task = 
                new AsyncTask<ScrumIssue, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(
                    ScrumIssue... params) {
                GoogleSession s;
                OperationStatus opStat = null;
                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    opStat = service.updateScrumIssue(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }
            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(Boolean.valueOf(result.getSuccess()));
                super.onPostExecute(result);
            }
        };
        task.execute(changes);
    }

    @Override
    public void remove(final Issue issue, final Callback<Boolean> callback) {
        AsyncTask<String, Void, OperationStatus> task = 
                new AsyncTask<String, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(
                    String... params) {
                GoogleSession session;
                OperationStatus opStat = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.removeScrumIssue(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(Boolean.valueOf(result.getSuccess()));
                super.onPostExecute(result);
            }

        };
        task.execute(issue.getKey());
    }

    @Override
    public void loadIssues(final MainTask mainTask, final Callback<List<Issue>> callback) {
        AsyncTask<String, Void, CollectionResponseScrumIssue> task =
                new AsyncTask<String, Void, CollectionResponseScrumIssue>() {

            @Override
            protected CollectionResponseScrumIssue doInBackground(String... params) {
                GoogleSession session;
                CollectionResponseScrumIssue issues = null;

                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    issues = service.loadScrumIssues(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return issues;
            }

            @Override
            protected void onPostExecute(CollectionResponseScrumIssue result) {
                List<ScrumIssue> resultItems = result.getItems();
                ArrayList<Issue> issues = new ArrayList<Issue>();
                if (resultItems != null) {
                    for (ScrumIssue s : resultItems) {
                        Issue.Builder issueBuilder = new Issue.Builder();
                        issueBuilder.setId(s.getKey());
                        issueBuilder.setName(s.getName());
                        issueBuilder.setDescription(s.getDescription());
                        issueBuilder.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(s.getStatus()));
                        issueBuilder.setEstimatedTime(s.getEstimation());
                        // TODO status(string) constructor
                        issues.add(issueBuilder.build());
                    }
                    callback.interactionDone(issues);
                }
            }
        };
        task.execute(mainTask.getKey());
    }

    @Override
    public void loadIssues(final Sprint sprint, final Callback<List<Issue>> cB) {
        AsyncTask<String, Void, CollectionResponseScrumIssue> task =
                new AsyncTask<String, Void, CollectionResponseScrumIssue>() {

            @Override
            protected CollectionResponseScrumIssue doInBackground(String... params) {
                GoogleSession session;
                CollectionResponseScrumIssue issues = null;

                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    // TODO not sure about the loadScrumIssues function
                    issues = service.loadScrumIssues(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return issues;
            }

            @Override
            protected void onPostExecute(CollectionResponseScrumIssue result) {
                List<ScrumIssue> resultItems = result.getItems();
                ArrayList<Issue> issues = new ArrayList<Issue>();
                if (resultItems != null) {
                    for (ScrumIssue s : resultItems) {
                        Issue.Builder issueBuilder = new Issue.Builder();
                        issueBuilder.setId(s.getKey());
                        issueBuilder.setName(s.getName());
                        issueBuilder.setDescription(s.getDescription());
                        issueBuilder.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(s.getStatus()));
                        issueBuilder.setEstimatedTime(s.getEstimation());
                        // TODO status(string) constructor
                        issues.add(issueBuilder.build());
                    }
                    cB.interactionDone(issues);
                }
            }
        };
        task.execute(sprint.getId());
    }

    @Override
    public void assignIssueToSprint(final Issue issue, final Sprint sprint, Callback<Boolean> cB) {
       
        AsyncTask<String, Void, OperationStatus> task = new AsyncTask<String, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(String... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertIssueInSprint(issue.getKey(), sprint.getId()).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO : redirecting to the login activity if not connected
                    e.printStackTrace();
                }
                return opStat;
            }
            
        };
        task.execute(issue.getKey(),sprint.getId());
    }

    @Override
    public void removeIssue(Issue issue, Sprint sprint, Callback<Boolean> cB) {
        // TODO Auto-generated method stub
        
    }

}