package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.MainTaskHandler;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;


/**
 * @author sylb, aschneuw, zenhaeus
 * 
 */
public class DSMainTaskHandler implements MainTaskHandler {

    @Override
    public void insert(final MainTask object, final Project project, final Callback<String> dbC) {
//        
//        // FIXME: Why scrumMainTask doesn't have a Priority field ?
//        scrumMainTask = new ScrumMainTask();
//        scrumMainTask.setName(object.getName());
//        scrumMainTask.setDescription(object.getDescription());
//        scrumMainTask.setStatus(object.getStatus().name());
//        scrumMainTask.setIssues(new ArrayList<ScrumIssue>());
//        Date date = new Date();
//        scrumMainTask.setLastModDate(date.getTime());
//        try {
//            scrumMainTask.setLastModUser(Session.getCurrentSession().getUser()
//                    .getEmail());
//        } catch (NotAuthenticatedException e) {
//            // TODO : redirecting to the login activity if not connected
//            e.printStackTrace();
//        }
//        
//        AsyncTask<ScrumMainTask, Void, String> task = new AsyncTask<ScrumMainTask, Void, String> (){
//            @Override
//            protected ScrumMainTask doInBackground(ScrumMainTask... params) {
//                try {
//                    GoogleSession s = (GoogleSession) Session.getCurrentSession();
//                    Scrumtool service = s.getAuthServiceObject();
//                    service.insertScrumMainTask(params[0]).execute();
//                } catch (IOException | NotAuthenticatedException e) {
//                    // TODO : redirecting to the login activity if not connected
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            
//            @Override
//            protected void onPostExecute(ScrumMainTask object) {
//                if (object != null) {
//                    dbC.interactionDone(Boolean.TRUE);
//                } else {
//                    dbC.interactionDone(Boolean.FALSE);
//                }
//            }
//        }
//        
//        im.execute(scrumMainTask);
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
        };
        task.execute(project.getId());
    }
    
    @Override
    public void load(final String key, final Callback<MainTask> cB) {
        AsyncTask<String, Void, ScrumMainTask> task = 
                new AsyncTask<String, Void, ScrumMainTask>(){
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
        };
        task.execute(key);
    }

    @Override
    public void update(final MainTask modified, final MainTask ref , final Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
    }

    @Override
    public void remove(final MainTask object, final Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
    }


    @Override
    public void insert(MainTask object, Callback<String> cB) {
        throw new UnsupportedOperationException();
        
    }
    
}