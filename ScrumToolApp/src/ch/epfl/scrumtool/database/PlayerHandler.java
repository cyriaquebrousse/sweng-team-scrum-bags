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
    void insert(final Player object, final Project project, final Callback<Player> cB);
    void loadPlayers(final Project project, final Callback<List<Player>> cB);
}
