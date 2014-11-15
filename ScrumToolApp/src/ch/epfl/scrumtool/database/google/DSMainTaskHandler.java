package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.MainTaskHandler;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;

/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSMainTaskHandler implements MainTaskHandler {

    @Override
    public void insert(final MainTask maintask, final String project,
            final Callback<MainTask> callback) {
    	
        ScrumMainTask scrumMaintask = new ScrumMainTask();
        scrumMaintask.setName(maintask.getName());
        scrumMaintask.setDescription(maintask.getDescription());
        scrumMaintask.setStatus(maintask.getStatus().name());
        scrumMaintask.setPriority(maintask.getPriority().name());
        scrumMaintask.setIssues(new ArrayList<ScrumIssue>());
        Date date = new Date();
        scrumMaintask.setLastModDate(date.getTime());
        try {
            scrumMaintask.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }

        AsyncTask<ScrumMainTask, Void, OperationStatus> task = new AsyncTask<ScrumMainTask, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumMainTask... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.insertScrumMainTask(project,
                            params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO : redirecting to the login activity if not connected
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus opStat) {
                MainTask.Builder maintaskBuilder = new MainTask.Builder(
                        maintask);
                maintaskBuilder.setKey(opStat.getKey());
                callback.interactionDone(maintaskBuilder.build());
            }
        };
        task.execute(scrumMaintask);
    }

    @Override
    public void loadMainTasks(final String project,
            final Callback<List<MainTask>> callback) {
        AsyncTask<String, Void, CollectionResponseScrumMainTask> task = 
                new AsyncTask<String, Void, CollectionResponseScrumMainTask>() {
            @Override
            protected CollectionResponseScrumMainTask doInBackground(
                    String... params) {
                GoogleSession session;
                CollectionResponseScrumMainTask mainTasks = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
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
                if (resulItems != null) {

                    for (ScrumMainTask s : resulItems) {
                        MainTask.Builder maintaskBuilder = new MainTask.Builder();
                        maintaskBuilder.setDescription(s.getDescription());
                        maintaskBuilder.setKey(s.getKey());
                        maintaskBuilder.setName(s.getName());
                        maintaskBuilder.setPriority(Priority.valueOf(s.getPriority()));
                        maintaskBuilder.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(s.getStatus()));
                        mainTasks.add(maintaskBuilder.build());
                    }
                    callback.interactionDone(mainTasks);
                } else {
                    Log.d("test", "warning the task list is empty");
                }
            }
        };
        task.execute(project);
    }

    @Override
    public void load(final String maintaskKey, final Callback<MainTask> callback) {
        throw new UnsupportedOperationException();
    }


    public void update(final MainTask modified, final MainTask ref, final String project,
            final Callback<Boolean> callback) {
        final ScrumMainTask changes = new ScrumMainTask();
        changes.setKey(modified.getKey());
        changes.setName(modified.getName());
        changes.setDescription(modified.getDescription());
        changes.setStatus(modified.getStatus().name());
        changes.setPriority(modified.getPriority().name());
        Date date = new Date();
        changes.setLastModDate(date.getTime());
        try {
            changes.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }

        AsyncTask<ScrumMainTask, Void, OperationStatus> task = new AsyncTask<ScrumMainTask, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(ScrumMainTask... params) {
                GoogleSession session;
                OperationStatus opStat = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.updateScrumMainTask(project, params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    callback.failure("Connection Error");
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(result.getSuccess());
            }

        };
        task.execute(changes);
    }

    @Override
    public void remove(final String maintask, final Callback<Boolean> callback) {
        AsyncTask<String, Void, OperationStatus> task = new AsyncTask<String, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(String... params) {
                GoogleSession session;
                OperationStatus opStat = null;
                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStat = service.removeScrumMainTask(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(Boolean.valueOf(result.getSuccess()));
                super.onPostExecute(result);
            }
        };
        task.execute(maintask);
    }

    @Override
    public void insert(MainTask maintask, Callback<MainTask> cB) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.database.DatabaseHandler#update(java.lang.Object,
     *  java.lang.Object, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void update(MainTask object, MainTask ref, Callback<Boolean> cB) {
        throw new UnsupportedOperationException();
    }

}