package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.SprintHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSSprintHandler implements SprintHandler {

    @Override
    /**
     * Inserts a Sprint on the server.
     */
    public void insert(final Sprint sprint, final Project project,
            final Callback<Sprint> callback) {
        ScrumSprint scrumSprint = new ScrumSprint();
        scrumSprint.setName(sprint.getTitle());
        scrumSprint.setDate(sprint.getDeadline());
        scrumSprint.setIssues(new ArrayList<ScrumIssue>());
        Date date = new Date();
        scrumSprint.setLastModDate(date.getTime());
        try {
            scrumSprint.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO This exception should probably be handled elsewhere
            e.printStackTrace();
        }

        new AsyncTask<ScrumSprint, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumSprint... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertScrumSprint(project.getKey(),
                            params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus opStat) {
                Sprint.Builder sprintBuilder = new Sprint.Builder(sprint);
                sprintBuilder.setKey(opStat.getKey());
                callback.interactionDone(sprintBuilder.build());
            }
        }.execute(scrumSprint);
    }

    @Override
    /**
     * Loads a Sprint from its key (String).
     */
    public void load(final String key, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();
    }

    @Override
    /**
     * Updates the ref Sprint to be the modified Sprint.
     */
    public void update(final Sprint modified, final Sprint ref,
            final Callback<Boolean> callback) {
        try {
            final GoogleSession session = (GoogleSession) Session
                    .getCurrentSession();
            final ScrumSprint scrumSprint = new ScrumSprint();
            scrumSprint.setKey(modified.getKey());
            scrumSprint.setName(modified.getTitle());
            scrumSprint.setDate(modified.getDeadline());
            scrumSprint.setLastModDate((new Date()).getTime());
            scrumSprint.setLastModUser(session.getUser().getEmail());

            new AsyncTask<ScrumSprint, Void, OperationStatus>() {

                @Override
                protected OperationStatus doInBackground(ScrumSprint... params) {
                    OperationStatus opStat = null;
                    try {
                        Scrumtool service = session.getAuthServiceObject();
                        opStat = service.updateScrumSprint(params[0]).execute();
                    } catch (IOException e) {
                        callback.failure("Connection error");
                    }
                    return opStat;
                }

                @Override
                protected void onPostExecute(OperationStatus result) {
                    callback.interactionDone(result.getSuccess());
                }
            }.execute(scrumSprint);

        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    /**
     * Removes a Sprint from the datastore.
     */
    public void remove(final Sprint sprint, final Callback<Boolean> callback) {
        new AsyncTask<String, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(String... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    opStat = session.getAuthServiceObject()
                            .removeScrumSprint(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;

            }

            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(result.getSuccess());
            }
        }.execute(sprint.getKey());
    }

    /**
     * Loads all the Sprints in the datastore.
     * 
     * @param projectKey
     * @param dbC
     */
    @Override
    public void loadSprints(final Project project,
            final Callback<List<Sprint>> callback) {
        new AsyncTask<String, Void, CollectionResponseScrumSprint>() {
            @Override
            protected CollectionResponseScrumSprint doInBackground(
                    String... params) {
                CollectionResponseScrumSprint sprints = null;
                try {
                    final GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    sprints = service.loadSprints(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return sprints;
            }

            @Override
            protected void onPostExecute(CollectionResponseScrumSprint result) {
                List<ScrumSprint> resultItems = result.getItems();
                ArrayList<Sprint> sprints = new ArrayList<Sprint>();
                if (resultItems != null) {
                    for (ScrumSprint s : resultItems) {
                        Sprint.Builder sprintBuilder = new Sprint.Builder();
                        sprintBuilder.setKey(s.getKey());
                        sprintBuilder.setTitle(s.getName());
                        sprintBuilder.setDeadline(s.getDate());
                        sprints.add(sprintBuilder.build());
                    }
                }
                callback.interactionDone(sprints);
            }
        }.execute(project.getKey());
    }

    @Override
    public void insert(final Sprint object, final Callback<Sprint> cB) {
        throw new UnsupportedOperationException();

    }
}
