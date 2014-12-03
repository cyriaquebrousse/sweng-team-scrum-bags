package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.containers.InsertPlayerArgs;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.PlayerConverters;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;

/**
 * Operations for Player
 * 
 * @author vincent
 */
public class PlayerOperations {

    public static final ScrumToolOperation<InsertPlayerArgs, InsertResponse<Player>> INSERT_PLAYER_TO_PROJECT =
            new ScrumToolOperation<InsertPlayerArgs, InsertResponse<Player>>() {

        @Override
        public InsertResponse<Player> operation(InsertPlayerArgs arg,
                Scrumtool service) throws IOException, ScrumToolException {
                Player.Builder player = new Player.Builder();
                User.Builder user = new User.Builder();
                user.setEmail(arg.getUserEmail()).setName(arg.getUserEmail());
                player.setRole(Role.valueOf(arg.getRole())).setIsAdmin(false)
                        .setUser(user.build());
                return new InsertResponse<Player>(player.build(), service
                        .addPlayerToProject(arg.getProjectKey(),
                                arg.getUserEmail(), arg.getRole()).execute());
        }
    };

    public static final ScrumToolOperation<Player, Void> UPDATE_PLAYER = 
            new ScrumToolOperation<Player, Void>() {
        @Override
        public Void operation(Player arg, Scrumtool service)
                throws IOException, ScrumToolException {
            ScrumPlayer scrumPlayer = PlayerConverters.PLAYER_TO_SCRUMPLAYER.convert(arg);
            return service.updateScrumPlayer(scrumPlayer).execute();
        }
    };

    public static final ScrumToolOperation<String, Void> DELETE_PLAYER =
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void operation(String arg, Scrumtool service) throws IOException {
            return service.removeScrumPlayer(arg).execute();
        }
    };

    public static final ScrumToolOperation<String, CollectionResponseScrumPlayer> LOAD_PLAYERS =
            new ScrumToolOperation<String, CollectionResponseScrumPlayer>() {

        @Override
        public CollectionResponseScrumPlayer operation(String arg,
                Scrumtool service) throws IOException {
            return service.loadPlayers(arg).execute();
        }
    };
}
