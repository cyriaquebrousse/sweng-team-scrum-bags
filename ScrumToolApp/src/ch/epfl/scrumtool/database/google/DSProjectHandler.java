package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * 
 * @author ?
 * 
 */
public class DSProjectHandler extends DatabaseHandler<Project> {
    private ScrumProject scrumProject;

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#insert(java.lang.Object)
     */
    @Override
    public void insert(Project object, Callback<Boolean> dbC) {
        scrumProject = new ScrumProject();
        scrumProject.setDescription(object.getDescription());
        scrumProject.setName(object.getName());
        scrumProject.setPlayers(new ArrayList<ScrumPlayer>());
        scrumProject.setBacklog(new ArrayList<ScrumMainTask>());
        Date date = new Date();
        scrumProject.setLastModDate(date.getTime());
        try {
            scrumProject.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO This exception should probably be handled elsewhere
            e.printStackTrace();
        }

        InsertProjectTask ip = new InsertProjectTask(dbC);
        ip.execute(scrumProject);
    }

    @Override
    public void load(String key, Callback<Project> dbC) {
        GetProjectTask task = new GetProjectTask(dbC);
        task.execute(key);

    }

    @Override
    public void loadAll(Callback<List<Project>> dbC) {
        // TODO Auto-generated method stub
        LoadAllProjectsTask task = new LoadAllProjectsTask(dbC);
        task.execute();

    }

    @Override
    public void update(Project modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
        // UpdateProjectTask task = new UpdateProjectTask(dbC);
        // task.execute(modified);

    }

    @Override
    public void remove(Project object, Callback<Boolean> dbC) {
        // TODO why pass a Project and not a String?
        // RemoveProjectTask task = new RemoveProjectTask(dbC);
        // task.execute(object);

    }

    /**
     * Loads the players of the passed project
     * 
     * @param projectKey
     * @param dbC
     */
    public void loadPlayers(String projectKey, Callback<List<Player>> dbC) {
        LoadPlayersTask task = new LoadPlayersTask(dbC);
        task.execute(projectKey);
    }

    /**
     * @param projectKey
     * @param dbC
     */
    public void loadMainTasks(String projectKey, Callback<List<MainTask>> dbC) {
        LoadMainTasksTask task = new LoadMainTasksTask(dbC);
        task.execute(projectKey);
    }

    /**
     * @param projectKey
     * @param dbC
     */
    public void loadSprints(String projectKey, Callback<List<Sprint>> dbC) {
        LoadSprintsTask task = new LoadSprintsTask(dbC);
        task.execute(projectKey);
    }

    private class LoadAllProjectsTask extends
            AsyncTask<Void, Void, CollectionResponseScrumProject> {

        private Callback<List<Project>> callback;

        public LoadAllProjectsTask(Callback<List<Project>> callback) {
            this.callback = callback;
        }

        @Override
        protected CollectionResponseScrumProject doInBackground(Void... params) {
            GoogleSession s;
            CollectionResponseScrumProject projects = null;
            try {
                s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                projects = service.loadAllProjects().execute();
            } catch (NotAuthenticatedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return projects;
        }

        @Override
        protected void onPostExecute(CollectionResponseScrumProject result) {
            List<ScrumProject> resultItems = result.getItems();
            ArrayList<Project> projects = new ArrayList<Project>();
            for (ScrumProject sP : resultItems) {
                Project.Builder pB = new Project.Builder();
                pB.setDescription(sP.getDescription());
                pB.setName(sP.getName());
                pB.setId(sP.getKey());
                projects.add(pB.build());
            }
            callback.interactionDone(projects);
        }

    }

    private class LoadMainTasksTask extends
            AsyncTask<String, Void, CollectionResponseScrumMainTask> {

        private Callback<List<MainTask>> cB;

        public LoadMainTasksTask(Callback<List<MainTask>> callBack) {
            this.cB = callBack;
        }

        @Override
        protected CollectionResponseScrumMainTask doInBackground(
                String... params) {
            GoogleSession s;
            CollectionResponseScrumMainTask mainTasks = null;
            try {
                s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                mainTasks = service.loadMainTasks(params[0]).execute();
            } catch (NotAuthenticatedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return mainTasks;
        }

        @Override
        protected void onPostExecute(CollectionResponseScrumMainTask result) {
            List<ScrumMainTask> resulItems = result.getItems();
            ArrayList<MainTask> mainTasks = new ArrayList<MainTask>();
            for (ScrumMainTask s : resulItems) {
                MainTask.Builder mB = new MainTask.Builder();
                mB.setDescription(s.getDescription());
                mB.setId(s.getKey());
                mB.setName(s.getName());
                // TODO a getPriority method and a status(String)
                // constructor
                // mB.setPriority(Priority.NORMAL);
                // mB.setStatus(s.getStatus());
                mainTasks.add(mB.build());
            }
            cB.interactionDone(mainTasks);
        }
    }

    private class LoadSprintsTask extends
            AsyncTask<String, Void, CollectionResponseScrumSprint> {
        private Callback<List<Sprint>> cB;

        public LoadSprintsTask(Callback<List<Sprint>> callBack) {
            this.cB = callBack;
        }

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
                sB.setId(s.getKey());
                sB.setDeadLine(new Date(s.getDate().getValue())); // TODO
                // Not so
                // sure
                // about
                // this
                sprints.add(sB.build());
            }
            cB.interactionDone(sprints);
        }
    }

    private class LoadPlayersTask extends
            AsyncTask<String, Void, CollectionResponseScrumPlayer> {

        private Callback<List<Player>> cB;

        public LoadPlayersTask(Callback<List<Player>> callBack) {
            this.cB = callBack;
        }

        @Override
        protected CollectionResponseScrumPlayer doInBackground(String... params) {
            GoogleSession s;
            CollectionResponseScrumPlayer players = null;

            try {
                s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                players = service.loadPlayers(params[0]).execute();
            } catch (NotAuthenticatedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return players;
        }

        @Override
        protected void onPostExecute(CollectionResponseScrumPlayer result) {
            List<ScrumPlayer> resultItems = result.getItems();
            ArrayList<Player> players = new ArrayList<Player>();
            for (ScrumPlayer s : resultItems) {
                Player.Builder pB = new Player.Builder();
                pB.setId(s.getKey());
                pB.setIsAdmin(s.getAdminFlag());
                // pB.setRole(s.getRole()); TODO get role and get user
                // pB.setUser(s.getUser());
                players.add(pB.build());
            }
            cB.interactionDone(players);
        }
    }

    /**
     * 
     * @author
     * 
     */
    private class GetProjectTask extends AsyncTask<String, Void, ScrumProject> {
        private Callback<Project> cB;

        public GetProjectTask(Callback<Project> cB) {
            this.cB = cB;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ScrumProject doInBackground(String... params) {
            ScrumProject project = null;
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                project = service.getScrumProject(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return project;
        }

        @Override
        protected void onPostExecute(ScrumProject sp) {
            Project.Builder pB = new Project.Builder();
            pB.setDescription(sp.getDescription());
            pB.setName(sp.getName());
            pB.setId(sp.getKey());
            cB.interactionDone(pB.build());
        }
    }

    /**
     * 
     * @author
     * 
     */
    private class InsertProjectTask extends
            AsyncTask<ScrumProject, Void, ScrumProject> {
        private Callback<Boolean> cB;

        public InsertProjectTask(Callback<Boolean> cB) {
            this.cB = cB;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ScrumProject doInBackground(ScrumProject... params) {
            ScrumProject proj = null;
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                proj = service.insertScrumProject(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return proj;
        }

        @Override
        protected void onPostExecute(ScrumProject su) {
            if (su != null) {
                cB.interactionDone(Boolean.TRUE);
            } else {
                cB.interactionDone(Boolean.FALSE);
            }
        }
    }

}