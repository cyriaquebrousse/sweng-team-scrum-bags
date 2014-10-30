/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;

/**
 * @author Arno
 * 
 */
public class DSUserHandler extends DatabaseHandler<User> {
    private ScrumUser scrumUser = new ScrumUser();
    public static final String LOCAL = "http://10.0.0.10:8888/_ah/api/";

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
        Date date = new Date();
        scrumUser.setLastModDate(date.getTime());
        scrumUser.setLastModUser(object.getEmail());
        scrumUser.setPlayers(new ArrayList<ch.epfl.scrumtool.server.scrumtool.model.Player>());

        InsertUserTask iu = new InsertUserTask();
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
    public void load(String key, DatabaseCallback<User> cB) {
        GetUserTask task = new GetUserTask(cB);
        task.execute(key);
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.entity.DatabaseHandler#getAll()
     */
    @Override
    public void loadAll(DatabaseCallback<User> cB) {
        // TODO Auto-generated method stub
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

    private class InsertUserTask extends AsyncTask<ScrumUser, Void, Void> {

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
            builder.setRootUrl(LOCAL);
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

    private class GetUserTask extends AsyncTask<String, Void, ScrumUser> {
    	private DatabaseCallback<User> cB;
    	
    	public GetUserTask(DatabaseCallback<User> cB){
    		this.cB = cB;
    	}
    	
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
            builder.setRootUrl(LOCAL);
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
        	User.Builder uB = new User.Builder();
            uB.setName(su.getName());
            uB.setEmail(su.getEmail());
            User user = uB.build();
            cB.interactionDone(user);
        }
    }

}
