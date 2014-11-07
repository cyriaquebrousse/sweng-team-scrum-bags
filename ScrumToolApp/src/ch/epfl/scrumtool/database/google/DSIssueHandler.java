package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSIssueHandler extends DatabaseHandler<Issue> {
    private ScrumIssue scrumIssue;

    @Override
    public void insert(Issue object, Callback<Boolean> dbC) {
        scrumIssue = new ScrumIssue();
        scrumIssue.setName(object.getName());
        scrumIssue.setDescription(object.getDescription());
        scrumIssue.setEstimation(object.getEstimatedTime());
        scrumIssue.setAssignedPlayer(new ScrumPlayer());
        Date date = new Date();
        scrumIssue.setLastModDate(date.getTime());
        scrumIssue.setLastModUser(object.getPlayer().getUser().getName());
        InsertIssueTask ii = new InsertIssueTask();
        ii.execute(scrumIssue);
    }

    @Override
    public void load(String key, Callback<Issue> dbC) {
        GetIssueTask task = new GetIssueTask(dbC);
        task.execute(key);
    }

    @Override
    public void update(Issue modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Issue object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    /**
     * 
     * @author
     * 
     */
    private class InsertIssueTask extends AsyncTask<ScrumIssue, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumIssue... params) {

            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumIssue(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * 
     * @author Arno
     * 
     */
    private class GetIssueTask extends AsyncTask<String, Void, ScrumIssue> {
        private Callback<Issue> cB;

        public GetIssueTask(Callback<Issue> cB) {
            this.cB = cB;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ScrumIssue doInBackground(String... params) {
            ScrumIssue issue = null;
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                issue = service.getScrumIssue(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return issue;
        }

        @Override
        protected void onPostExecute(ScrumIssue si) {
            Issue.Builder iB = new Issue.Builder();
            iB.setId("test"); // TODO change this value
            iB.setName(si.getName());
            iB.setDescription(si.getDescription());
            iB.setStatus(ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT); // TODO
                                                                            // change
                                                                            // this
                                                                            // value
            iB.setEstimatedTime(si.getEstimation());
            Issue issue = iB.build(); // TODO need to add the player
            cB.interactionDone(issue);
        }
    }
}