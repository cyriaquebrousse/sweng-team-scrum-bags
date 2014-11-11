/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;

/**
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
}
