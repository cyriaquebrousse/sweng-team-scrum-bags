package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;

public class DSSprintHandler extends DatabaseHandler<Sprint> {

    @Override
    public void insert(Sprint object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(String key, Callback<Sprint> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Sprint modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Sprint object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    public void loadIssues(String mainTaskKey, Callback<List<Issue>> callback) {
        LoadIssuesTask task = new LoadIssuesTask(callback);
        task.execute(mainTaskKey);
    }

    private class LoadIssuesTask extends
            AsyncTask<String, Void, CollectionResponseScrumIssue> {

        private Callback<List<Issue>> callback;

        public LoadIssuesTask(Callback<List<Issue>> callback) {
            this.callback = callback;
        }

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
            for (ScrumIssue s : resultItems) {
                Issue.Builder issueBuilder = new Issue.Builder();
                issueBuilder.setDescription(s.getDescription());
                issueBuilder.setEstimatedTime(s.getEstimation());
                issueBuilder.setId(s.getKey());
                issueBuilder.setName(s.getName());
                // TODO status(string) constructor
                // iB.setStatus(s.getStatus());
                issues.add(issueBuilder.build());
            }
            callback.interactionDone(issues);
        }

    }

}
