package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
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
    public void insert(final Sprint object, final Callback<Sprint> cB) {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Loads a Sprint from its key (String).
     */
    public void load(final String key, final Callback<Sprint> cB) {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Updates the ref Sprint to be the modified Sprint.
     */
    public void update(final Sprint modified, final Sprint ref, final Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Removes a Sprint from the datastore.
     */
    public void remove(Sprint object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
    }

    /**
     * Loads all the Sprints in the datastore.
     * @param projectKey
     * @param dbC
     */
    @Override
    public void loadSprints(final Project project, final  Callback<List<Sprint>> cB) {
        AsyncTask<String, Void, CollectionResponseScrumSprint> task = 
                new AsyncTask<String, Void, CollectionResponseScrumSprint>() {
            @Override
            protected CollectionResponseScrumSprint doInBackground(String... params) {
                GoogleSession s;
                CollectionResponseScrumSprint sprints = null;
                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
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
                for (ScrumSprint s : resultItems) {
                    Sprint.Builder sB = new Sprint.Builder();
                    sB.setKey(s.getKey());
                    //sB.setDeadline(s.getDate().getValue()); // TODO
                    // Not so
                    // sure
                    // about
                    // this
                    sprints.add(sB.build());
                }
                cB.interactionDone(sprints);
            }
        };
        task.execute(project.getKey());
    }

    @Override
    /**
     * Inserts a Sprint in a Project.
     */
    public void insert(Sprint sprint, Project project, Callback<Sprint> cB) {
        // TODO Auto-generated method stub
    }
    @Override
    /**
     * Removes a Sprint from a Project.
     */
    public void removeSprint(Sprint sprint, Project project,
            Callback<Boolean> cB) {
        // TODO Auto-generated method stub
    }
}
