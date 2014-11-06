package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DoubleEntityDatabaseHandler;
import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSMainTaskHandler extends
        DoubleEntityDatabaseHandler<MainTask, Issue> {
    private ScrumMainTask scrumMainTask;
    private ScrumIssue scrumIssue;

    @Override
    public void insert(MainTask object, Callback<Boolean> dbC) {
        scrumMainTask = new ScrumMainTask();
        scrumMainTask.setName(object.getName());
        scrumMainTask.setDescription(object.getDescription());
        scrumMainTask.setStatus("FINISHED"); // TODO change this value
        scrumMainTask.setIssues(new ArrayList<ScrumIssue>());
        scrumMainTask.setLastModDate(new Date().getTime());
        try {
            scrumMainTask.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        InsertMainTaskTask im = new InsertMainTaskTask();
        im.execute(scrumMainTask);
    }

    @Override
    public void insert(MainTask mainTask, Issue object, Callback<Boolean> dbC) {

        scrumIssue = new ScrumIssue();
        scrumIssue.setName(object.getName());
        scrumIssue.setDescription(object.getDescription());
        scrumIssue.setEstimation(object.getEstimatedTime());
        scrumIssue.setAssignedPlayer(new ScrumPlayer());
        Date date = new Date();
        scrumIssue.setLastModDate(date.getTime());
        scrumIssue.setLastModUser(object.getPlayer().getUser().getName());

        List<ScrumIssue> scrumIssueList = new ArrayList<ScrumIssue>();
        scrumIssueList.add(scrumIssue);

        scrumMainTask = new ScrumMainTask();
        scrumMainTask.setName(mainTask.getName());
        scrumMainTask.setDescription(mainTask.getDescription());
        scrumMainTask.setStatus("FINISHED"); // TODO chang this value
        scrumMainTask.setIssues(scrumIssueList);
        scrumMainTask.setLastModDate(new Date().getTime());
        try {
            scrumMainTask.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        InsertMainTaskTask im = new InsertMainTaskTask();
        im.execute(scrumMainTask);
        InsertIssueTask ii = new InsertIssueTask();
        ii.execute(scrumIssue);
    }

    @Override
    public void load(String key, Callback<MainTask> dbC) {
        // GetMainTaskTask task = new GetMainTaskTask(dbC);
        // task.execute(key);
    }

    @Override
    public void loadAll(Callback<List<MainTask>> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(MainTask modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(MainTask object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }
    
    public void loadIssues(String mainTaskKey, Callback<List<Issue>> dbC) {
         LoadIssuesTask task = new LoadIssuesTask(dbC);
         task.execute(mainTaskKey);
    }

    private class InsertIssueTask extends AsyncTask<ScrumIssue, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumIssue... params) {
        	GoogleSession s = (GoogleSession) Session.getCurrentSession();
            Scrumtool service = s.getAuthServiceObject();
            try {
                service.insertScrumIssue(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * 
     * @author ?
     *
     */
    private class GetIssueTask extends AsyncTask<String, Void, ScrumIssue> {
        private Callback<Issue> cB;

        public GetIssueTask(Callback<Issue> cB) {
            this.cB = cB;
        }
        @Override
        protected ScrumIssue doInBackground(String... params) {
            GoogleSession s = (GoogleSession) Session.getCurrentSession();
            Scrumtool service = s.getAuthServiceObject();
            ScrumIssue issue = null;
            try {
                issue = service.getScrumIssue(params[0]).execute();
            } catch (IOException e) {
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

    private class InsertMainTaskTask extends AsyncTask<ScrumMainTask, Void, Void> {
        @Override
        protected Void doInBackground(ScrumMainTask... params) {
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumMainTask(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetMainTaskTask extends AsyncTask<String, Void, ScrumMainTask> {
        private Callback<MainTask> cB;
        
        public GetMainTaskTask(Callback<MainTask> cB) {
            this.cB = cB;
        }
        
        @Override
        protected ScrumMainTask doInBackground(String... params) {
            GoogleSession s = (GoogleSession) Session.getCurrentSession();
            Scrumtool service = s.getAuthServiceObject();
            ScrumMainTask mainTask = null;
            try {
                mainTask = service.getScrumMainTask(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return mainTask;
        }

        @Override
        protected void onPostExecute(ScrumMainTask sm) {
            MainTask.Builder mB = new MainTask.Builder();
            mB.setId("test"); // TODO change this value
            mB.setName(sm.getName());
            mB.setDescription(sm.getDescription());
            mB.setStatus(ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT); // TODO
                                                                            // change
                                                                            // this
                                                                            // value
            MainTask mainTask = mB.build(); // TODO need to add the player
            cB.interactionDone(mainTask);
        }
    }
    
    private class LoadIssuesTask extends
        AsyncTask<String, Void, CollectionResponseScrumIssue> {
    
        private Callback<List<Issue>> cB;
        
        public LoadIssuesTask(Callback<List<Issue>> callBack) {
        	this.cB = callBack;
        }
        
        @Override
        protected CollectionResponseScrumIssue doInBackground(String... params) {
        	GoogleSession s = (GoogleSession) Session.getCurrentSession();
            Scrumtool service = s.getAuthServiceObject();
            CollectionResponseScrumIssue issues = null;
            try {
                issues = service.loadScrumIssues(params[0]).execute();
            } catch (NotAuthenticatedException | IOException e) {
                e.printStackTrace();
            }
            return issues;
        }
        
        @Override
        protected void onPostExecute(CollectionResponseScrumIssue result) {
            List<ScrumIssue> resultItems = result.getItems();
            ArrayList<Issue> issues = new ArrayList<Issue>();
            for (ScrumIssue scrumIssue : resultItems) {
                Player.Builder pB = new Player.Builder();
                pB.setId("playser");
                pB.setRole(Role.DEVELOPER);
                pB.setIsAdmin(false);
                Player player = pB.build();
                
                Issue.Builder iB = new Issue.Builder();
                iB.setId(scrumIssue.getKey());
                iB.setName(scrumIssue.getName());
                iB.setDescription(scrumIssue.getDescription());
                iB.setPlayer(player); //TODO load player function for the issue
//                iB.setStatus(); // TODO
                iB.setEstimatedTime(scrumIssue.getEstimation());
                issues.add(iB.build());
                }
            cB.interactionDone(issues);
            }
}