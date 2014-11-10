/**
 * 
 */
package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author aschneuw
 * 
 */
public class DSPlayerHandler implements PlayerHandler {

    @Override
    public void insert(final Player object, final Project project, final Callback<Player> dbC) {

        ScrumPlayer player = new ScrumPlayer();
        player.setAdminFlag(object.isAdmin());
        Date date = new Date();
        player.setLastModDate(date.getTime());
        player.setLastModDate(date.getTime());
        try {
            player.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO This exception should probably be handled elsewhere
            e.printStackTrace();
        }
        player.setRole(object.getRole().name());
        

        AsyncTask<ScrumPlayer, Void, OperationStatus> task = new AsyncTask<ScrumPlayer, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumPlayer... params) {
                OperationStatus opStatus = null;
                try {
                    GoogleSession s = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = s.getAuthServiceObject();
                    opStatus = service.insertScrumPlayer(project.getId(), object.getId(), params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStatus;
            }
        };
        task.execute(player);

        // TODO finish this method
    }

    @Override
    public void load(final String key, final Callback<Player> dbC) {
        throw new UnsupportedOperationException();

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
    public void insert(Player object, Callback<Player> cB) {
        throw new UnsupportedOperationException();
        
    }
}
