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
    /**
     * Load the Sprints of a Project
     * 
     * @param project
     * @param cB
     */
    void loadSprints(final Project project, final Callback<List<Sprint>> cB);

    /**
     * Insert a Sprint in a Project
     * 
     * @param sprint
     * @param project
     * @param cB
     */
    void insert(final Sprint sprint, final Project project,
            final Callback<Sprint> cB);

    /**
     * Removes a Sprint from a Project
     * 
     * @param sprint
     * @param project
     * @param cB
     */
    void removeSprint(final Sprint sprint, final Project project,
            final Callback<Boolean> cB);
}
