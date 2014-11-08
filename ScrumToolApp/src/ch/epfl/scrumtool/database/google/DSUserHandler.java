package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSUserHandler implements UserHandler {

    @Override
    public void insert(User object, Callback<String> dbC) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the User in the Datastore for the corresponding mailaddress
     * (creates one automatically if it doesn't exist)
     * @email user mail address
     * @cB callback
     */
    @Override
    public void loginUser(final String email, final Callback<User> cB) {
        AsyncTask<String, Void, ScrumUser>  task = new AsyncTask<String, Void, ScrumUser>(){
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
        };
        task.execute(email);
    }

    @Override
    public void load(final String key, final Callback<User> cB) {
        AsyncTask<String, Void, ScrumUser> task = new AsyncTask<String, Void, ScrumUser>(){
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
        };
        task.execute(key);
    }

    @Override
    public void update(User modified, User ref, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(User object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub
    }
    
    
}
