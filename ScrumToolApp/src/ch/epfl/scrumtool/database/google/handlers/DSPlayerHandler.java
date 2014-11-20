/**
 * 
 */
package ch.epfl.scrumtool.database.google.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.PlayerHandler;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.PlayerOperations;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
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
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * @author aschneuw
 * 
 */
public class DSPlayerHandler implements PlayerHandler {

    @Override
    public void insert(final Player player, final Project project,
            final Callback<Player> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(final String playerKey, final Callback<Player> callback) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void update(final Player modified, final Player ref,
            final Callback<Boolean> callback) {
        DSExecArgs.Factory<Player, OperationStatus, Boolean> builder = new DSExecArgs.Factory<Player, OperationStatus, Boolean>(
                MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        builder.setOperation(PlayerOperations.UPDATE_PLAYER);
        OperationExecutor.execute(modified, builder.build());
    }

    @Override
    public void remove(final Player player, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = new DSExecArgs.Factory<String, OperationStatus, Boolean>(
                MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(PlayerOperations.DELETE_PLAYER);
        OperationExecutor.execute(player.getKey(), factory.build());
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
                    userBuilder.setEmail(s.getUser().getEmail()).setName(
                            s.getUser().getName());
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

    /**
     * Add a new Player to a Project
     */
    @Override
    public void addPlayerToProject(final Project project,
            final String userEmail, final Role role,
            final Callback<Player> callback) {
        new AsyncTask<String, Void, ScrumPlayer>() {
            @Override
            protected ScrumPlayer doInBackground(String... params) {
                ScrumPlayer scrumPlayer = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    Scrumtool service = session.getAuthServiceObject();
                    scrumPlayer = service.addPlayerToProject(project.getKey(),
                            userEmail, role.name()).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return scrumPlayer;
            }

            @Override
            protected void onPostExecute(ScrumPlayer scrumPlayer) {
                if (scrumPlayer != null) {
                    User.Builder userBuilder = new User.Builder();
                    userBuilder.setEmail(scrumPlayer.getUser().getEmail())
                            .setName(scrumPlayer.getUser().getName());
                    Player.Builder playerBuilder = new Player.Builder();
                    playerBuilder.setKey(scrumPlayer.getKey())
                            .setRole(Role.valueOf(scrumPlayer.getRole()))
                            .setUser(userBuilder.build())
                            .setIsAdmin(scrumPlayer.getAdminFlag());
                    callback.interactionDone(playerBuilder.build());
                } else {
                    callback.failure("Unable to create client");
                }
            }
        }.execute();
    }
}