/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;

/**
 * @author aschneuw
 * 
 */
public interface MainTaskHandler extends DatabaseHandler<MainTask> {
    /**
     * Load the Maintask of a Project
     * 
     * @param projectKey
     * @param cB
     */
    void loadMainTasks(final String projectKey, Callback<List<MainTask>> cB);

    /**
     * Insert a Maintask in a Project
     * @param object
     * @param projectKey
     * @param cB
     */
    void insert(final MainTask object, final String projectKey,
            final Callback<MainTask> cB);
    
    void update(final MainTask object, final MainTask ref, final String project,
            final Callback<Boolean> cb);
}

