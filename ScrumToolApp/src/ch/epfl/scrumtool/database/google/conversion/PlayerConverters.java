package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
import ch.epfl.scrumtool.util.Preconditions;

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
            assert dbPlayer != null;
            
            Preconditions.throwIfNull("Trying to convert a Player with null parameters",
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
            assert player != null;

            ScrumPlayer dbPlayer = new ScrumPlayer();
            dbPlayer.setKey(player.getKey());
            dbPlayer.setAdminFlag(player.isAdmin());
            dbPlayer.setRole(player.getRole().name());

            ScrumUser dbUser = new ScrumUser();
            dbUser.setEmail(player.getUser().getEmail());
            dbPlayer.setUser(dbUser);
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
