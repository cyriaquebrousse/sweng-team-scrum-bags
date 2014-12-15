/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Project;

/**
 * 
 * Defines database operations related to a project
 * @author aschneuw
 * 
 */
public interface ProjectHandler extends DatabaseHandler<Project> {
    /**
     * Load the projects of the logged User
     * 
     * @param cB
     */
    void loadProjects(final Callback<List<Project>> cB);
}
