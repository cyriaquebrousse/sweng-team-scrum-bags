package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * 
 * @author aschneuw
 * 
 */

public class DSUserHandler extends DatabaseHandler<User> {

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#insert(java.lang.Object)
     */
    @Override
    public void insert(User object, Callback<Boolean> dbC) {
        // TODO
        // No implementation needed -> exception
    }

    public void loginUser(String email, Callback<User> cB) {
        LoginTask l = new LoginTask(cB);
        l.execute(email);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.entity.DatabaseHandler#get(com.google.api.client.util
     * .Key)
     */
    @Override
    public void load(String key, Callback<User> cB) {
        GetUserTask task = new GetUserTask(cB);
        task.execute(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#getAll()
     */
    @Override
    public void loadAll(Callback<List<User>> cB) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#update(java.lang.Object)
     */
    @Override
    public void update(User modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#remove(java.lang.Object)
     */
    @Override
    public void remove(User object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    public void loadProjects(String userKey, Callback<List<Project>> dbC) {
        LoadProjectsTask task = new LoadProjectsTask(dbC);
        task.execute(userKey);
    }

    public void addProject(String userKey, Project p, Callback<String> dbC) {

    }

    /**
     * @author Vincent
     * 
     */
    private class LoadProjectsTask extends
            AsyncTask<String, Void, List<ScrumProject>> {
        private Callback<List<Project>> cB;

        public LoadProjectsTask(Callback<List<Project>> callBack) {
            this.cB = callBack;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected List<ScrumProject> doInBackground(String... params) {
            Scrumtool service = GoogleSession.getServiceObject();
            List<ScrumProject> projects = null;
            // TODO How do I load a list of projects from DB
//            try {
//                projects = service.loadProjects(params[0]).execute();
//            }catch (IOException e){
//             // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            return projects;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(List<ScrumProject> result) {
            ArrayList<Project> projects = new ArrayList<Project>();
            for (ScrumProject sP : result) {
                Project.Builder pB = new Project.Builder();
                pB.setDescription(sP.getDescription());
                pB.setName(sP.getName());
                pB.setId(sP.getKey());
                projects.add(pB.build());
            }
            cB.interactionDone(projects);
        }

    }

    /**
     * @author zenhaeus
     * 
     */
    private class LoginTask extends AsyncTask<String, Void, ScrumUser> {
        private Callback<User> cB;

        public LoginTask(Callback<User> cB) {
            this.cB = cB;
        }

        @Override
        protected ScrumUser doInBackground(String... params) {

            Scrumtool service = GoogleSession.getServiceObject();
            ScrumUser user = null;
            try {
                user = service.loginUser(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(ScrumUser su) {
            User.Builder uB = new User.Builder();
            uB.setName(su.getName());
            uB.setEmail(su.getEmail());
            User user = uB.build();
            cB.interactionDone(user);
        }

    }

    /**
     * 
     * @author
     * 
     */
    private class GetUserTask extends AsyncTask<String, Void, ScrumUser> {
        private Callback<User> cB;

        public GetUserTask(Callback<User> cB) {
            this.cB = cB;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ScrumUser doInBackground(String... params) {
            Scrumtool service = GoogleSession.getServiceObject();
            ScrumUser user = null;
            try {
                user = service.getScrumUser(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(ScrumUser su) {
            User.Builder uB = new User.Builder();
            uB.setName(su.getName());
            uB.setEmail(su.getEmail());
            User user = uB.build();
            cB.interactionDone(user);
        }
    }

}
