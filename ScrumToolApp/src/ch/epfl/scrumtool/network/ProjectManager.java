package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 */
public interface ProjectManager {
    /**
     * Load the Projects of a User
     * 
     * @param user
     * @param cB
     */
    void loadProjects(User user, Callback<List<Project>> cB);

    /**
     * Update a Project
     */
    void updateProject();

    /**
     * Delete a Project
     */
    void deleteProject();
}
