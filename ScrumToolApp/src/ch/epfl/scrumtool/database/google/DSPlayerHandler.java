/**
 * 
 */
package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.PlayerHandler;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author aschneuw
 * 
 */
public class DSPlayerHandler implements PlayerHandler {

    @Override
    public void insert(final Player object, final Project project, final Callback<String> dbC) {

        ScrumPlayer player = new ScrumPlayer();
        player.setAdminFlag(object.isAdmin());

        AsyncTask<ScrumPlayer, Void, String> task = new AsyncTask<ScrumPlayer, Void, String>() {
            @Override
            protected String doInBackground(ScrumPlayer... params) {
                try {
                    GoogleSession s = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    service.insertScrumPlayer(params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute(player);

        // TODO finish this method
    }

    @Override
    public void load(final String key, final Callback<Player> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(final Player modified, final Player ref,
            final Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Player object, Callback<Boolean> dbC) {
        // TODO Auto-generated method stub

    }

    /**
     * Loads the players of the passed project
     * 
     * @param projectKey
     * @param dbC
     */
    @Override
    public void loadPlayers(final Project project,
            final Callback<List<Player>> cB) {
        AsyncTask<String, Void, CollectionResponseScrumPlayer> task = 
                new AsyncTask<String, Void, CollectionResponseScrumPlayer>() {
            @Override
            protected CollectionResponseScrumPlayer doInBackground(
                    String... params) {
                GoogleSession s;
                CollectionResponseScrumPlayer players = null;

                try {
                    s = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    players = service.loadPlayers(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return players;
            }

            @Override
            protected void onPostExecute(CollectionResponseScrumPlayer result) {
                List<ScrumPlayer> resultItems = result.getItems();
                ArrayList<Player> players = new ArrayList<Player>();
                for (ScrumPlayer s : resultItems) {
                    Player.Builder pB = new Player.Builder();
                    pB.setId(s.getKey());
                    pB.setIsAdmin(s.getAdminFlag());
                    // pB.setRole(s.getRole()); TODO get role and get user
                    // pB.setUser(s.getUser());
                    players.add(pB.build());
                }
                cB.interactionDone(players);
            }
        };

        task.execute(project.getId());
    }

    @Override
    public void insert(Player object, Callback<String> cB) {
        throw new UnsupportedOperationException();
        
    }
}
