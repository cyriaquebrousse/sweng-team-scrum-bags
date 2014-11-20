package ch.epfl.scrumtool.database.google.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.IssueHandler;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.IssueOperations;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

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
    public void insert(final Issue issue, final MainTask maintask,
            final Callback<Issue> callback) {
        try {

            scrumIssue = new ScrumIssue();
            scrumIssue.setName(issue.getName());
            scrumIssue.setDescription(issue.getDescription());
            scrumIssue.setStatus(issue.getStatus().name());
            scrumIssue.setEstimation(issue.getEstimatedTime());
            scrumIssue.setPriority(issue.getPriority().name());
            scrumIssue.setLastModDate((new Date()).getTime());

            final GoogleSession session = (GoogleSession) Session
                    .getCurrentSession();

            scrumIssue.setLastModUser(session.getUser().getEmail());
            final String playerKey;
            if (issue.getPlayer() != null) {
                playerKey = issue.getPlayer().getKey();
            } else {
                playerKey = null;
            }
            final String sprintKey;
            if (issue.getSprint() != null) {
                sprintKey = issue.getSprint().getKey();
            } else {
                sprintKey = null;
            }
            AsyncTask<ScrumIssue, Void, OperationStatus> task = new AsyncTask<ScrumIssue, Void, OperationStatus>() {
                @Override
                protected OperationStatus doInBackground(ScrumIssue... params) {
                    OperationStatus opStat = null;
                    try {

                        Scrumtool service = session.getAuthServiceObject();
                        
                        opStat = service.insertScrumIssue(maintask.getKey(), params[0])
                                .setPlayerKey(playerKey).setSprintKey(sprintKey).execute();
                    } catch (IOException e) {
                        callback.failure("Issue could not be inserted on the Database");
                    }
                    return opStat;
                }

                @Override
                protected void onPostExecute(OperationStatus opStat) {
                    Issue.Builder issueBuilder = new Issue.Builder(issue);
                    issueBuilder.setKey(opStat.getKey());
                    callback.interactionDone(issueBuilder.build());
                }
            };
            task.execute(scrumIssue);
        } catch (NotAuthenticatedException e) {
            callback.failure("Not authenticated");
        }
    }

    @Override
    public void load(final String issueKey, final Callback<Issue> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Issue modified, final Issue ref,
            final Callback<Boolean> callback) {
        final ScrumIssue changes = new ScrumIssue();
        changes.setKey(modified.getKey());
        changes.setName(modified.getName());
        changes.setDescription(modified.getDescription());
        changes.setStatus(modified.getStatus().name());
        changes.setEstimation(modified.getEstimatedTime());
        changes.setPriority(modified.getPriority().name());
        Date date = new Date();
        changes.setLastModDate(date.getTime());
        final ScrumPlayer scrumPlayer = new ScrumPlayer();
        if (modified.getPlayer() != null) {
            scrumPlayer.setKey(modified.getPlayer().getKey());
            changes.setAssignedPlayer(scrumPlayer); //scrumPlayer has only it's key set
        } else {
            changes.setAssignedPlayer(null);
        }
        final ScrumSprint scrumSprint = new ScrumSprint();
        if (modified.getSprint() != null) {
            scrumSprint.setKey(modified.getSprint().getKey());
            changes.setSprint(scrumSprint);
        } else {
            changes.setSprint(null);
        }
        try {
            changes.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }
        AsyncTask<ScrumIssue, Void, OperationStatus> task = new AsyncTask<ScrumIssue, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumIssue... params) {
                GoogleSession session;
                OperationStatus opStat = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.updateScrumIssue(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            /*
             * (non-Javadoc)
             * 
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(Boolean.valueOf(result.getSuccess()));
            }
        };
        task.execute(changes);
    }

    @Override
    public void remove(final Issue issue, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(IssueOperations.DELETE_ISSUE);
        OperationExecutor.execute(issue.getKey(), factory.build());
    }

    @Override
    public void loadIssues(final MainTask mainTask,
            final Callback<List<Issue>> callback) {
        AsyncTask<String, Void, CollectionResponseScrumIssue> task = 
                new AsyncTask<String, Void, CollectionResponseScrumIssue>() {

            @Override
            protected CollectionResponseScrumIssue doInBackground(
                    String... params) {
                GoogleSession session;
                CollectionResponseScrumIssue issues = null;

                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    issues = service.loadIssuesByMainTask(params[0]).execute();
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
                        issueBuilder.setKey(s.getKey());
                        issueBuilder.setName(s.getName());
                        issueBuilder.setDescription(s.getDescription());
                        issueBuilder.setStatus(ch.epfl.scrumtool.entity.Status
                                .valueOf(s.getStatus()));
                        issueBuilder.setEstimatedTime(s.getEstimation());
                        issueBuilder.setPriority(Priority.valueOf(s
                                .getPriority()));
                        issues.add(issueBuilder.build());
                    }
                }
                // TODO better error handling
                callback.interactionDone(issues);
            }
        };
        task.execute(mainTask.getKey());
    }

    @Override
    public void loadIssues(final Sprint sprint, final Callback<List<Issue>> cB) {
        AsyncTask<String, Void, CollectionResponseScrumIssue> task = 
                new AsyncTask<String, Void, CollectionResponseScrumIssue>() {

            @Override
            protected CollectionResponseScrumIssue doInBackground(
                    String... params) {
                GoogleSession session;
                CollectionResponseScrumIssue issues = null;

                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    // TODO not sure about the loadScrumIssues function
                    issues = service.loadIssuesBySprint(params[0]).execute();
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
                        issueBuilder.setKey(s.getKey());
                        issueBuilder.setName(s.getName());
                        issueBuilder.setDescription(s.getDescription());
                        issueBuilder.setStatus(ch.epfl.scrumtool.entity.Status
                                .valueOf(s.getStatus()));
                        issueBuilder.setEstimatedTime(s.getEstimation());
                        issueBuilder.setPriority(Priority.valueOf(s
                                .getPriority()));
                        issues.add(issueBuilder.build());
                    }
                    cB.interactionDone(issues);
                }
            }
        };
        task.execute(sprint.getKey());
    }

    @Override
    public void assignIssueToSprint(final Issue issue, final Sprint sprint,
            Callback<Boolean> cB) {

        AsyncTask<String, Void, OperationStatus> task = new AsyncTask<String, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(String... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertIssueInSprint(issue.getKey(),
                            sprint.getKey()).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO : redirecting to the login activity if not connected
                    e.printStackTrace();
                }
                return opStat;
            }

        };
        task.execute(issue.getKey(), sprint.getKey());
    }

    @Override
    public void removeIssue(Issue issue, Sprint sprint, Callback<Boolean> cB) {
        // TODO Auto-generated method stub

    }

}