/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author aschneuw
 *
 */
public interface SprintHandler extends DatabaseHandler<Sprint> {
    void loadSprints(final Project project, final Callback<List<Sprint>> cB);
    void insert(final Sprint sprint, final Project project, final Callback<Sprint> cB);
    void removeSprint(final Sprint sprint, final Project project, final Callback<Boolean> cB);
}
