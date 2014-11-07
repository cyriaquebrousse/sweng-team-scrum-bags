package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DoubleEntityDatabaseHandler;
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
        
        // FIXME: Why scrumMainTask doesn't have a Priority field ?
        scrumMainTask = new ScrumMainTask();
        scrumMainTask.setName(object.getName());
        scrumMainTask.setDescription(object.getDescription());
        scrumMainTask.setStatus(object.getStatus().name());
        scrumMainTask.setIssues(new ArrayList<ScrumIssue>());
        Date date = new Date();
        scrumMainTask.setLastModDate(date.getTime());
        try {
            scrumMainTask.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }
        InsertMainTaskTask im = new InsertMainTaskTask(dbC);
        im.execute(scrumMainTask);
    }

    @Override
    public void insert(MainTask mainTask, Issue object, Callback<Boolean> dbC) {

        scrumIssue = new ScrumIssue();
        scrumIssue.setName(object.getName());
        scrumIssue.setDescription(object.getDescription());
        scrumIssue.setEstimation(object.getEstimatedTime());
        //scrumIssue.setAssignedPlayer(new ScrumPlayer());
        Date date = new Date();
        scrumIssue.setLastModDate(date.getTime());
        try {
            scrumIssue.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }

        List<ScrumIssue> scrumIssueList = new ArrayList<ScrumIssue>();
        scrumIssueList.add(scrumIssue);

        scrumMainTask = new ScrumMainTask();
        scrumMainTask.setName(mainTask.getName());
        scrumMainTask.setDescription(mainTask.getDescription());
        scrumMainTask.setStatus(mainTask.getStatus().name());
        scrumMainTask.setIssues(scrumIssueList);
        scrumMainTask.setLastModDate(new Date().getTime());
        try {
            scrumMainTask.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }

        InsertMainTaskTask im = new InsertMainTaskTask(dbC);
        im.execute(scrumMainTask);
        InsertIssueTask ii = new InsertIssueTask(dbC);
        ii.execute(scrumIssue);
    }

    
    public void addIssueToMainTask(MainTask task, Issue object, Callback<Boolean> dbC) {
    
        scrumIssue = new ScrumIssue();
        scrumIssue.setName(object.getName());
        scrumIssue.setDescription(object.getDescription());
        scrumIssue.setEstimation(object.getEstimatedTime());
        //scrumIssue.setAssignedPlayer(new ScrumPlayer());
        Date date = new Date();
        scrumIssue.setLastModDate(date.getTime());
        try {
            scrumIssue.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }
        InsertIssueTask ii = new InsertIssueTask(dbC);
        ii.execute(scrumIssue);
    }
    
    @Override
    public void load(String key, Callback<MainTask> dbC) {
        GetMainTaskTask task = new GetMainTaskTask(dbC);
        task.execute(key);
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

    /**
     * 
     * @author aschneuw
     *
     */
    private class InsertIssueTask extends AsyncTask<ScrumIssue, Void, ScrumIssue> {
        
        private Callback<Boolean> dbC;
        
        public InsertIssueTask(Callback<Boolean> dbC) {
            this.dbC = dbC;
        }
        
        @Override
        protected ScrumIssue doInBackground(ScrumIssue... params) {
            try {
            	GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumIssue(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO : redirecting to the login activity if not connected
                e.printStackTrace();
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(ScrumIssue object) {
            if (object != null) {
                dbC.interactionDone(Boolean.TRUE);
            } else {
                dbC.interactionDone(Boolean.FALSE);
            }
        }

    }

    /**
     * 
     * @author aschneuw
     *
     */
    private class GetIssueTask extends AsyncTask<String, Void, ScrumIssue> {
        private Callback<Issue> cB;

        public GetIssueTask(Callback<Issue> cB) {
            this.cB = cB;
        }
        @Override
        protected ScrumIssue doInBackground(String... params) {
            ScrumIssue issue = null;
            try {
            	GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                issue = service.getScrumIssue(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO : redirecting to the login activity if not connected
                e.printStackTrace();
            }
            return issue;
        }

        @Override
        protected void onPostExecute(ScrumIssue si) {
            Issue.Builder iB = new Issue.Builder();
            // TODO : id should be generated by the server
            iB.setId("test");
            iB.setName(si.getName());
            iB.setDescription(si.getDescription());
            iB.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(si.getStatus()));
            iB.setEstimatedTime(si.getEstimation());
            // TODO need to add the player 
            Issue issue = iB.build(); 
            cB.interactionDone(issue);
        }
    }

    /**
     * 
     * @author aschneuw
     *
     */
    private class InsertMainTaskTask extends AsyncTask<ScrumMainTask, Void, ScrumMainTask> {
        
        private Callback<Boolean> dbC;
        
        public InsertMainTaskTask(Callback<Boolean> dbC) {
            this.dbC = dbC;
        }
        @Override
        protected ScrumMainTask doInBackground(ScrumMainTask... params) {
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumMainTask(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO : redirecting to the login activity if not connected
                e.printStackTrace();
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(ScrumMainTask object) {
            if (object != null) {
                dbC.interactionDone(Boolean.TRUE);
            } else {
                dbC.interactionDone(Boolean.FALSE);
            }
        }
    }

    /**
     * 
     * @author aschneuw
     *
     */
    private class GetMainTaskTask extends AsyncTask<String, Void, ScrumMainTask> {
        private Callback<MainTask> cB;
        
        public GetMainTaskTask(Callback<MainTask> cB) {
            this.cB = cB;
        }
        
        @Override
        protected ScrumMainTask doInBackground(String... params) {
            ScrumMainTask mainTask = null;
            try {
            	GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                mainTask = service.getScrumMainTask(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO : redirecting to the login activity if not connected
                e.printStackTrace();
            }
            return mainTask;
        }

        @Override
        protected void onPostExecute(ScrumMainTask sm) {
            MainTask.Builder mB = new MainTask.Builder();
            // TODO : id should be generated by the server
            mB.setId("test");
            mB.setName(sm.getName());
            mB.setDescription(sm.getDescription());
            mB.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(sm.getStatus()));
            // TODO need to add the player. 
            // loris : what player ? it is a MainTask not an Issue
            MainTask mainTask = mB.build();
            cB.interactionDone(mainTask);
        }
    }
    
    /**
     * 
     * @author aschneuw
     *
     */
    private class LoadIssuesTask extends
        AsyncTask<String, Void, CollectionResponseScrumIssue> {
    
        private Callback<List<Issue>> cB;
        
        public LoadIssuesTask(Callback<List<Issue>> callBack) {
        	this.cB = callBack;
        }
        
        @Override
        protected CollectionResponseScrumIssue doInBackground(String... params) {
            CollectionResponseScrumIssue issues = null;
            try {
            	GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                issues = service.loadScrumIssues(params[0]).execute();
            } catch (NotAuthenticatedException | IOException e) {
                // TODO : redirecting to the login activity if not connected
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
                // TODO : id should be generated by the server
                pB.setId("playser");
                pB.setRole(Role.DEVELOPER);
                pB.setIsAdmin(false);
                Player player = pB.build();
                
                Issue.Builder iB = new Issue.Builder();
                iB.setId(scrumIssue.getKey());
                iB.setName(scrumIssue.getName());
                iB.setDescription(scrumIssue.getDescription());
                iB.setPlayer(player);
                // TODO : load player function for the issue
                iB.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(scrumIssue.getStatus()));
                iB.setEstimatedTime(scrumIssue.getEstimation());
                issues.add(iB.build());
            }
            cB.interactionDone(issues);
        }
    }
}