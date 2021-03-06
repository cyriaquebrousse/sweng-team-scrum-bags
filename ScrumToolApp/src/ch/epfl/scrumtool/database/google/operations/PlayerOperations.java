package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertPlayerArgs;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.PlayerConverters;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * Operations for Player
 * 
 * @author vincent
 */
public final class PlayerOperations {

    /**
     * Operation to add a player to a given project
     */
    public static final ScrumToolOperation<InsertPlayerArgs, InsertResponse<Player>> INSERT_PLAYER_TO_PROJECT =
        new ScrumToolOperation<InsertPlayerArgs, InsertResponse<Player>>() {

            @Override
            public InsertResponse<Player> operation(InsertPlayerArgs arg,
                Scrumtool service) throws IOException, ScrumToolException {
                Player.Builder player = new Player.Builder();
                User.Builder user = new User.Builder();
                user.setEmail(arg.getKey()).setName(arg.getKey());
                player.setRole(Role.valueOf(arg.getRole())).setIsAdmin(false)
                        .setUser(user.build());
                return new InsertResponse<Player>(player.build(), 
                        service.addPlayerToProject(arg.getProjectKey(),
                                arg.getKey(), arg.getRole()).execute());
            }
        };

    /**
     * operation to update a player 
     */
    public static final ScrumToolOperation<Player, Void> UPDATE_PLAYER = 
        new ScrumToolOperation<Player, Void>() {
            @Override
            public Void operation(Player arg, Scrumtool service)throws IOException, ScrumToolException {
                ScrumPlayer scrumPlayer = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(arg);
                return service.updateScrumPlayer(scrumPlayer).execute();
            }
        };
    
    /**
     * operation to set a given player as project administrator
     */
    public static final ScrumToolOperation<String, Void> SET_PLAYER_AS_ADMIN =
        new ScrumToolOperation<String, Void>() {
            @Override
            public Void operation(String arg, Scrumtool service) throws IOException, ScrumToolException {
                return service.setPlayerAsAdmin(arg).execute();
            }
        };

    /**
     * operation to delete the given player on the database
     */
    public static final ScrumToolOperation<String, Void> DELETE_PLAYER =
        new ScrumToolOperation<String, Void>() {
            @Override
            public Void operation(String arg, Scrumtool service) throws IOException {
                return service.removeScrumPlayer(arg).execute();
            }
        };

    /**
     * operation to load the players for a given project
     */
    public static final ScrumToolOperation<String, CollectionResponseScrumPlayer> LOAD_PLAYERS =
        new ScrumToolOperation<String, CollectionResponseScrumPlayer>() {

            @Override
            public CollectionResponseScrumPlayer operation(String arg,
                    Scrumtool service) throws IOException {
                return service.loadPlayers(arg).execute();
            }
        };

    /**
     * operation to load project invitations for the current user
     */
    public static final ScrumToolOperation<String, CollectionResponseScrumPlayer> LOAD_INVITED_PLAYERS = 
        new ScrumToolOperation<String, CollectionResponseScrumPlayer>() {

            @Override
            public CollectionResponseScrumPlayer operation(String arg,
                    Scrumtool service) throws IOException {
                return service.loadInvitedPlayers().execute();
            }
        };
}
