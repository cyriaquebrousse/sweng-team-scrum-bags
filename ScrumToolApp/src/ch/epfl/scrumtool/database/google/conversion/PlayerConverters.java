package ch.epfl.scrumtool.database.google.conversion;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
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
public class PlayerConverters {

    public static final EntityConverter<ScrumPlayer, Player> SCRUMPLAYER_TO_PLAYER = 
            new EntityConverter<ScrumPlayer, Player>() {

        @Override
        public Player convert(ScrumPlayer dbPlayer) {
            assertTrue(dbPlayer != null);
            throwIfNull("Trying to convert a Player with null parameters",
                    dbPlayer.getKey(), dbPlayer.getAdminFlag(), dbPlayer.getRole(), dbPlayer.getUser());
                    
            Player.Builder builder = new Player.Builder();

            String key = dbPlayer.getKey();
            if (key != null) {
                builder.setKey(key);
            }

            Boolean isAdmin = dbPlayer.getAdminFlag();
            if (isAdmin != null) {
                builder.setIsAdmin(isAdmin);
            }
            
            Boolean isInvited = dbPlayer.getInvitedFlag();
            if (isInvited != null) {
                builder.setIsInvited(isInvited);
            }
            
            ScrumProject dbProject = dbPlayer.getProject();
            if (dbProject != null) {
                Project project = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(dbProject);
                builder.setProject(project);
            }

            String role = dbPlayer.getRole();
            if (role != null) {
                builder.setRole(Role.valueOf(role));
            }

            ScrumUser dbUser = dbPlayer.getUser();
            if (dbUser != null) {
                User user = UserConverters.SCRUMUSER_TO_USER.convert(dbUser);
                builder.setUser(user);
            }

            return builder.build();
        }
    };

    public static final EntityConverter<Player, ScrumPlayer> PLAYER_TO_SCRUMPLAYER = 
            new EntityConverter<Player, ScrumPlayer>() {

        @Override
        public ScrumPlayer convert(Player player) {
            assertTrue(player != null);

            ScrumPlayer dbPlayer = new ScrumPlayer();
            dbPlayer.setKey(player.getKey());
            dbPlayer.setInvitedFlag(player.isInvited());
            dbPlayer.setAdminFlag(player.isAdmin());
            dbPlayer.setRole(player.getRole().name());

            ScrumUser dbUser = new ScrumUser();
            dbUser.setEmail(player.getUser().getEmail());
            dbPlayer.setUser(dbUser);
            
            ScrumProject dbProject = new ScrumProject();
            dbProject.setKey(player.getProject().getKey());
            dbPlayer.setProject(dbProject);
            // Currently we don't need LastModDate and LasModUser

            return dbPlayer;
        }

    };
    
    public static final EntityConverter<InsertResponse<Player>, Player> OPSTATPLAYER_TO_PLAYER = 
            new EntityConverter<InsertResponse<Player>, Player>() {

        @Override
        public Player convert(InsertResponse<Player> a) {
            return a.getEntity()
                    .getBuilder()
                    .setKey(a.getkeyReponse().getKey())
                    .build();
        }
    };

}
