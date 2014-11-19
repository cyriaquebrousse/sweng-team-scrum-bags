package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
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
            assert dbPlayer != null;
            Player.Builder builder = new Player.Builder();

            String key = dbPlayer.getKey();
            if (key != null) {
                builder.setKey(key);
            }

            Boolean isAdmin = dbPlayer.getAdminFlag();
            if (isAdmin != null) {
                builder.setIsAdmin(isAdmin);
            }

            Role role = Role.valueOf(dbPlayer.getRole());
            if (role != null) {
                builder.setRole(role);
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

}
