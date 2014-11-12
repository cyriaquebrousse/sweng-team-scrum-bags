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
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
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
        scrumPlayer.setIssues(new ArrayList<ScrumIssue>());
        scrumPlayer.setLastModDate(date.getTime());
        scrumPlayer.setRole(player.getRole().name());

        try {
            scrumPlayer.setLastModUser(Session.getCurrentSession().getUser()
                    .getEmail());
        } catch (NotAuthenticatedException e) {
            // TODO This exception should probably be handled elsewhere
            e.printStackTrace();
        }

        new AsyncTask<ScrumPlayer, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumPlayer... params) {
                OperationStatus opStatus = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    opStatus = service.insertScrumPlayer(project.getKey(),
                            player.getKey(), params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStatus;
            }

            @Override
            protected void onPostExecute(OperationStatus opStat) {
                Player.Builder playerBuilder = new Player.Builder(player);
                playerBuilder.setKey(opStat.getKey());
                callback.interactionDone(playerBuilder.build());
            }
        }.execute(scrumPlayer);

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
    public void remove(final Player player, final Callback<Boolean> callback) {
        new AsyncTask<String, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(String... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    opStat = session.getAuthServiceObject()
                            .removeScrumProject(params[0]).execute();
                } catch (NotAuthenticatedException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus result) {
                callback.interactionDone(result.getSuccess());
            }

        }.execute(player.getKey());

    }

    @Override
    public void loadPlayers(final Project project,
            final Callback<List<Player>> calback) {
        new AsyncTask<String, Void, CollectionResponseScrumPlayer>() {
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
                    User.Builder userBuilder = new User.Builder();
                    userBuilder.setEmail(s.getUser().getEmail())
                            .setName(s.getUser().getName());
                    Player.Builder playerBuilder = new Player.Builder();
                    playerBuilder.setKey(s.getKey())
                            .setIsAdmin(s.getAdminFlag())
                            .setRole(Role.valueOf(s.getRole()))
                            .setUser(userBuilder.build());
                    players.add(playerBuilder.build());
                }
                calback.interactionDone(players);
            }
        }.execute(project.getKey());
    }

    @Override
    public void insert(Player player, Callback<Player> callback) {
        throw new UnsupportedOperationException();

    }
}
