/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;

/**
 * Defines database operations related to player
 * 
 * @author Arno
 * 
 */
public interface PlayerHandler extends DatabaseHandler<Player> {
    /**
     * Insert a Player in a Project
     * 
     * @param player
     * @param project
     * @param cB
     */
    void insert(final Player player, final Project project,
            final Callback<Player> cB);

    /**
     * Load the Players of a Project
     * 
     * @param project
     * @param cB
     */
    void loadPlayers(final Project project, final Callback<List<Player>> cB);

    /**
     * Add a User to a Project (via a Player)
     * 
     * @param project
     * @param userEmail
     * @param role
     * @param callback
     */
    void addPlayerToProject(final Project project, final String userEmail,
            final Role role, final Callback<Player> callback);

    /**
     * Loads the the players that have been invited for the currentUser
     * 
     * @param callback
     */
    void loadInvitedPlayers(Callback<List<Player>> callback);

    /**
     * Sets the player as admin for his associated project
     * 
     * @param player
     * @param callback
     */
    void setPlayerAsAdmin(Player player, Callback<Void> callback);

}
