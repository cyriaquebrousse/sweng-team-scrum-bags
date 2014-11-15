/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author aschneuw
 * 
 */
public interface SprintHandler extends DatabaseHandler<Sprint> {
    /**
     * Load the Sprints of a Project
     * 
     * @param projectKey
     * @param cB
     */
    void loadSprints(final String projectKey, final Callback<List<Sprint>> cB);

    /**
     * Insert a Sprint in a Project
     * 
     * @param sprint
     * @param project
     * @param cB
     */
    void insert(final Sprint sprint, final String project,
            final Callback<Sprint> cB);

}
