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
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSProjectHandler implements ProjectHandler {

    @Override
    public void insert(final Project project, final Callback<Project> callback) {
        ScrumProject scrumProject;
        scrumProject = new ScrumProject();
        scrumProject.setDescription(project.getDescription());
        scrumProject.setName(project.getName());
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

        AsyncTask<ScrumProject, Void, OperationStatus> task = new AsyncTask<ScrumProject, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumProject... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertScrumProject(params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus opStat) {
                Project.Builder projectBuilder = new Project.Builder(project);
                projectBuilder.setKey(opStat.getKey());
                callback.interactionDone(projectBuilder.build());
            }
        };
        task.execute(scrumProject);
    }

    @Override
    public void load(final String projectKey, final Callback<Project> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Project modified, final Project ref,
            final Callback<Boolean> callback) {
        // TODO Auto-generated method stub
        // UpdateProjectTask task = new UpdateProjectTask(dbC);
        // task.execute(modified);

    }

    @Override
    public void remove(final Project project, final Callback<Boolean> callback) {
        // TODO why pass a Project and not a String?
        // RemoveProjectTask task = new RemoveProjectTask(dbC);
        // task.execute(object);

        AsyncTask<String, Void, OperationStatus> task = new AsyncTask<String, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(String... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    opStat = session.getAuthServiceObject()
                            .removeScrumProject(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;

            }

            /*
             * (non-Javadoc)
             * 
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(result.getSuccess());
            }
        };
        task.execute(project.getKey());

    }

    @Override
    public void loadProjects(final Callback<List<Project>> callback) {
        AsyncTask<Void, Void, CollectionResponseScrumProject> task = new AsyncTask<Void, Void, CollectionResponseScrumProject>() {
            protected CollectionResponseScrumProject doInBackground(
                    Void... params) {
                GoogleSession session;
                CollectionResponseScrumProject projects = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    projects = service.loadProjects(
                            session.getUser().getEmail()).execute();
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
                if (resultItems != null) {
                    for (ScrumProject sP : resultItems) {
                        Project.Builder projectBuilder = new Project.Builder();
                        projectBuilder.setDescription(sP.getDescription());
                        projectBuilder.setName(sP.getName());
                        projectBuilder.setKey(sP.getKey());
                        projects.add(projectBuilder.build());
                    }
                }
                callback.interactionDone(projects);
            }
        };
        task.execute();
    }

}