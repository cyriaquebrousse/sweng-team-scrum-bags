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
    public void insert(final Player player, final Project project,
            final Callback<Player> callback) {

        ScrumPlayer scrumPlayer = new ScrumPlayer();
        scrumPlayer.setAdminFlag(player.isAdmin());
        Date date = new Date();
        scrumPlayer.setLastModDate(date.getTime());
        scrumPlayer.setLastModDate(date.getTime());
        try {
            scrumPlayer.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO This exception should probably be handled elsewhere
            e.printStackTrace();
        }
        scrumPlayer.setRole(player.getRole().name());

        AsyncTask<ScrumPlayer, Void, OperationStatus> task = new AsyncTask<ScrumPlayer, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumPlayer... params) {
                OperationStatus opStatus = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStatus = service.insertScrumPlayer(project.getId(),
                            player.getId(), params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStatus;
            }
        };
        task.execute(scrumPlayer);

        // TODO finish this method
    }

    @Override
    public void load(final String playerKey, final Callback<Player> callback) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void update(final Player modified, final Player ref,
            final Callback<Boolean> callback) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Player object, Callback<Boolean> callback) {
        // TODO Auto-generated method stub

    }

    
    @Override
    public void loadPlayers(final Project project,
            final Callback<List<Player>> calback) {
        AsyncTask<String, Void, CollectionResponseScrumPlayer> task = new AsyncTask<String, Void, CollectionResponseScrumPlayer>() {
            @Override
            protected CollectionResponseScrumPlayer doInBackground(
                    String... params) {
                GoogleSession session;
                CollectionResponseScrumPlayer players = null;

                try {
                    session = (GoogleSession) Session.getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
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
                    Player.Builder playerBuilder = new Player.Builder();
                    playerBuilder.setId(s.getKey());
                    playerBuilder.setIsAdmin(s.getAdminFlag());
                    // pB.setRole(s.getRole()); TODO get role and get user
                    // pB.setUser(s.getUser());
                    players.add(playerBuilder.build());
                }
                calback.interactionDone(players);
            }
        };

        task.execute(project.getId());
    }

    @Override
    public void insert(Player player, Callback<Player> callback) {
        throw new UnsupportedOperationException();

    }
}
