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
    public void insert(final MainTask object, final Project project, final Callback<MainTask> cB) {
        ScrumMainTask scrumMainTask = new ScrumMainTask();
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

        AsyncTask<ScrumMainTask, Void, OperationStatus> task = new AsyncTask<ScrumMainTask, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumMainTask... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    opStat = service.insertScrumMainTask(project.getKey(), params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO : redirecting to the login activity if not connected
                    e.printStackTrace();
                }
                return opStat;
            }
            @Override
            protected void onPostExecute(OperationStatus opStat) {
                MainTask.Builder builder = new MainTask.Builder(object);
                builder.setId(opStat.getKey());
                cB.interactionDone(builder.build());
            }
        };
        task.execute(scrumMainTask);
    }

    @Override
    public void loadMainTasks(final Project project, final Callback<List<MainTask>> cB) {
        AsyncTask<String, Void, CollectionResponseScrumMainTask> task = 
                new AsyncTask<String, Void, CollectionResponseScrumMainTask>() {
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
                if (resulItems != null) {

                    for (ScrumMainTask s : resulItems) {
                        MainTask.Builder mB = new MainTask.Builder();
                        mB.setDescription(s.getDescription());
                        mB.setId(s.getKey());
                        mB.setName(s.getName());
                        // TODO a getPriority method and a status(String)
                        // constructor
                        mB.setPriority(ch.epfl.scrumtool.entity.Priority.NORMAL);
                        mB.setStatus(ch.epfl.scrumtool.entity.Status.valueOf(s.getStatus()));
                        mainTasks.add(mB.build());
                    }
                    cB.interactionDone(mainTasks);
                } else {
                    Log.d("test", "warning the task list is empty");
                }
            } 
        };
        task.execute(project.getKey());
    }

    @Override
    public void load(final String key, final Callback<MainTask> cB) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final MainTask modified, final MainTask ref , final Callback<Boolean> cB) {
        final ScrumMainTask changes = new ScrumMainTask();
        changes.setKey(modified.getKey());
        changes.setName(modified.getName());
        changes.setDescription(modified.getDescription());
        changes.setStatus(modified.getStatus().name());
        Date date = new Date();
        changes.setLastModDate(date.getTime());
        try {
            changes.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO : redirecting to the login activity if not connected
            e.printStackTrace();
        }

        AsyncTask<ScrumMainTask, Void, OperationStatus> task = 
                new AsyncTask<ScrumMainTask, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(
                    ScrumMainTask... params) {
                GoogleSession s;
                OperationStatus opStat = null;
                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    opStat = service.updateScrumMainTask(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                cB.interactionDone(Boolean.valueOf(result.getSuccess()));
                super.onPostExecute(result);
            }

        };
        task.execute(changes);
    }

    @Override
    public void remove(final MainTask object, final Callback<Boolean> cB) {
        AsyncTask<String, Void, OperationStatus> task = 
                new AsyncTask<String, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(
                    String... params) {
                GoogleSession s;
                OperationStatus opStat = null;
                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    opStat = service.removeScrumMainTask(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            /* (non-Javadoc)
             * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
             */
            @Override
            protected void onPostExecute(OperationStatus result) {
                cB.interactionDone(Boolean.valueOf(result.getSuccess()));
                super.onPostExecute(result);
            }

        };
        task.execute(object.getKey());
    }

    @Override
    public void insert(MainTask object, Callback<MainTask> cB) {
        throw new UnsupportedOperationException();
    }

}