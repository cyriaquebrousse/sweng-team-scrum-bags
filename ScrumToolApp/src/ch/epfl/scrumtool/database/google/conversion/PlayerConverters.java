package ch.epfl.scrumtool.database.google.conversion;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * Ensures conversion between ScrumPlayer and Player
 * 
 * @author vincent
 * 
 */
public final class PlayerConverters {

    public static final EntityConverter<ScrumPlayer, Player> SCRUMPLAYER_TO_PLAYER = 
            new EntityConverter<ScrumPlayer, Player>() {

        @Override
        public Player convert(ScrumPlayer dbPlayer) {
            throwIfNull("Trying to convert a Player with null parameters",
                    dbPlayer.getKey(),
                    dbPlayer.getAdminFlag(),
                    dbPlayer.getRole(),
                    dbPlayer.getUser(),
                    dbPlayer.getInvitedFlag());
            
            Player.Builder builder = new Player.Builder();

            String key = dbPlayer.getKey();
            builder.setKey(key);

            Boolean isAdmin = dbPlayer.getAdminFlag();
            builder.setIsAdmin(isAdmin);

            Boolean isInvited = dbPlayer.getInvitedFlag();
            builder.setIsInvited(isInvited);

            Project project = null;
            ScrumProject dbProject = dbPlayer.getProject();
            if (dbProject != null) {
                project = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(dbProject);
            }
            builder.setProject(project);

            String role = dbPlayer.getRole();
            builder.setRole(Role.valueOf(role));

            ScrumUser dbUser = dbPlayer.getUser();
            User user = UserConverters.SCRUMUSER_TO_USER.convert(dbUser);
            builder.setUser(user);

            return builder.build();
        }
    };

    public static final EntityConverter<Player, ScrumPlayer> PLAYER_TO_SCRUMPLAYER = 
            new EntityConverter<Player, ScrumPlayer>() {

        @Override
        public ScrumPlayer convert(Player player) {
            ScrumPlayer dbPlayer = new ScrumPlayer();
            
            if (!player.getKey().equals("")) {
                dbPlayer.setKey(player.getKey());
            }
            
            dbPlayer.setInvitedFlag(player.isInvited());
            dbPlayer.setAdminFlag(player.isAdmin());
            dbPlayer.setRole(player.getRole().name());

            ScrumUser dbUser = new ScrumUser();
            dbUser.setEmail(player.getUser().getEmail());
            dbPlayer.setUser(dbUser);
            
            return dbPlayer;
        }

    };
    
    public static final EntityConverter<InsertResponse<Player>, Player> INSERTRESPONSE_TO_PLAYER = 
            new EntityConverter<InsertResponse<Player>, Player>() {

        @Override
        public Player convert(InsertResponse<Player> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getKeyReponse().getKey())
                    .build();
        }
    };

}
