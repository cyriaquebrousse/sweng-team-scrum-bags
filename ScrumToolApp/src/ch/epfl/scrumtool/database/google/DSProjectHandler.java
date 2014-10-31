package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class DSProjectHandler extends DatabaseHandler<Project> {
private ScrumProject scrumProject;
    /* (non-Javadoc)
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
        scrumProject.setLastModUser(Session.getCurrentSession().getCurrentUser().getEmail());
        
        InsertProjectTask ip = new InsertProjectTask();
        ip.execute(scrumProject);
    }

    @Override
    public void load(String key, Callback<Project> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadAll(Callback<Project> dbC) {
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
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(AppEngineUtils.getServerURL());
            Scrumtool service = builder.build();
            ScrumProject project = null;
            try {
                project = service.getScrumProject(params[0]).execute();
            } catch (IOException e) {
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
    
    private class InsertProjectTask extends AsyncTask<ScrumProject, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumProject... params) {
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(AppEngineUtils.getServerURL());
            Scrumtool service = builder.build();

            try {
                service.insertScrumProject(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

}