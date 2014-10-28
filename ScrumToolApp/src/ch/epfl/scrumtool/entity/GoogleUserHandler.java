/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * @author Arno
 * 
 */
public class GoogleUserHandler extends DatabaseHandler<User> {
    private ScrumUser scrumUser = new ScrumUser();

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#insert(java.lang.Object)
     */
    @Override
    public void insert(User object) {
        scrumUser = new ScrumUser();
        scrumUser.setEmail(object.getEmail());
        scrumUser.setName(object.getEmail());

        InsertUser iu = new InsertUser();
        iu.execute(scrumUser);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.entity.DatabaseHandler#get(com.google.api.client.util
     * .Key)
     */
    @Override
    public User get(String key) {
        GetUser iu = new GetUser();
        ScrumUser su = null;
        try {
            su = iu.execute(key).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("ASYNC_RESULT", "failure");
            e.printStackTrace();
        }
        
        User.Builder uB = new User.Builder();
        uB.setName(su.getName());
        uB.setEmail(su.getEmail());
        HashSet<String> projects;
        if (su.getProjects() != null) {
            projects = new HashSet<String>(su.getProjects());
        } else {
            projects = new HashSet<String>();
        }
        uB.addProjects(projects);
        User user = uB.build();
        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#getAll()
     */
    @Override
    public List<User> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#update(java.lang.Object)
     */
    @Override
    public void update(User modified) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#remove(java.lang.Object)
     */
    @Override
    public void remove(User object) {
        // TODO Auto-generated method stub

    }

    private class InsertUser extends AsyncTask<ScrumUser, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumUser... params) {
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(Scrumtool.DEFAULT_ROOT_URL);
            Scrumtool service = builder.build();

            try {
                service.insertScrumUser(params[0]).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

    private class GetUser extends AsyncTask<String, Void, ScrumUser> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ScrumUser doInBackground(String... params) {
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(Scrumtool.DEFAULT_ROOT_URL);
            Scrumtool service = builder.build();
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
            Log.d("DB_TEST", su.getEmail());
            Log.d("DB_TEST", su.getName());
        }
    }

}
