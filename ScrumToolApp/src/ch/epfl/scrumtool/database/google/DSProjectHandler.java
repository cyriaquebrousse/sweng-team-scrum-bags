package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

public class DSProjectHandler extends DatabaseHandler<Project> {
    private ScrumProject scrumProject;

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#insert(java.lang.Object)
     */
    @Override
    public void insert(Project object) {
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

        InsertProjectTask ip = new InsertProjectTask();
        ip.execute(scrumProject);
    }

    @Override
    public void load(String key, Callback<Project> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadAll(String filter, Callback<List<Project>> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Project modified) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Project object) {
        // TODO Auto-generated method stub

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
    private class InsertProjectTask extends AsyncTask<ScrumProject, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumProject... params) {
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumProject(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

}