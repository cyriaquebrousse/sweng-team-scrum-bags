/**
 * 
 */
package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.DatabaseCallback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;

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
    public void insert(Player object) {
        InsertPlayerTask task = new InsertPlayerTask();
        ScrumPlayer player = new ScrumPlayer();
        player.setAdminFlag(object.isAdmin());
        //

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#load(java.lang.String,
     * ch.epfl.scrumtool.database.DatabaseCallback)
     */
    @Override
    public void load(String key, DatabaseCallback<Player> dbC) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.database.DatabaseHandler#loadAll(ch.epfl.scrumtool.
     * database.DatabaseCallback)
     */
    @Override
    public void loadAll(DatabaseCallback<Player> dbC) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#update(java.lang.Object)
     */
    @Override
    public void update(Player modified) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.scrumtool.database.DatabaseHandler#remove(java.lang.Object)
     */
    @Override
    public void remove(Player object) {
        // TODO Auto-generated method stub

    }

    private class InsertPlayerTask extends AsyncTask<ScrumPlayer, Void, Void> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(ScrumPlayer... params) {
			Scrumtool service = AppEngineUtils.getServiceObject();
			try {
				service.insertScrumPlayer(params[0]).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
        

    }

}
