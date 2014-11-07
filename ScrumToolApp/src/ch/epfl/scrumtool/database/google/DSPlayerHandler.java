/**
 * 
 */
package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author aschneuw
 * 
 */
public class DSPlayerHandler extends DatabaseHandler<Player> {

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#insert(java.lang.Object)
     */
    @Override
    public void insert(Player object, Callback<Boolean> dbC) {
        InsertPlayerTask task = new InsertPlayerTask();
        ScrumPlayer player = new ScrumPlayer();
        player.setAdminFlag(object.isAdmin());
        // TODO finish this method

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#load(java.lang.String,
     * ch.epfl.scrumtool.database.DatabaseCallback)
     */
    @Override
    public void load(String key, Callback<Player> dbC) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#update(java.lang.Object)
     */
    @Override
    public void update(Player modified, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#remove(java.lang.Object)
     */
    @Override
    public void remove(Player object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }
/**
 * 
 * @author 
 *
 */
    private class InsertPlayerTask extends AsyncTask<ScrumPlayer, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(ScrumPlayer... params) {
            try {
                GoogleSession s = (GoogleSession) Session.getCurrentSession();
                Scrumtool service = s.getAuthServiceObject();
                service.insertScrumPlayer(params[0]).execute();
            } catch (IOException | NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

}
