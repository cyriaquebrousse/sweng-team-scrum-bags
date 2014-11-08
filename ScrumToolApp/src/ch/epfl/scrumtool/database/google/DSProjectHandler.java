package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.ProjectHandler;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSProjectHandler implements ProjectHandler {
    private ScrumProject scrumProject;

    @Override
    public void insert(final Project object, final Callback<String> cB) {
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
        
        AsyncTask<ScrumProject, Void, ScrumProject> task = 
                new AsyncTask<ScrumProject, Void, ScrumProject>(){
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
                    cB.interactionDone(su.getKey());
                } else {
                    cB.interactionDone("");
                }
            }
        };
        task.execute(scrumProject);
    }

    @Override
    public void load(final String key, final Callback<Project> cB) {
        AsyncTask<String, Void, ScrumProject> task =
                new AsyncTask<String, Void, ScrumProject>() {
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
        };
        
        task.execute(key);
    }

    @Override
    public void update(final Project modified, final Project ref, final Callback<Boolean> dbC) {
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






    
    public void loadProjects(final Callback<List<Project>> cB) {
        AsyncTask<Void, Void, CollectionResponseScrumProject> task = 
                new AsyncTask<Void, Void, CollectionResponseScrumProject>(){
            protected CollectionResponseScrumProject doInBackground(Void... params) {
                GoogleSession s;
                CollectionResponseScrumProject projects = null;
                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    projects = service.loadProjects(s.getUser().getEmail()).execute();
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
                cB.interactionDone(projects);
            }
        };
        task.execute();
    }
    

}