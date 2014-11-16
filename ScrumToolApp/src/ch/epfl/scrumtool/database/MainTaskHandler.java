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
     * @param project
     * @param cB
     */
    void loadMainTasks(final Project project, Callback<List<MainTask>> cB);

    /**
     * Insert a Maintask in a Project
     * @param object
     * @param project
     * @param cB
     */
    void insert(final MainTask object, final Project project,
            final Callback<MainTask> cB);
    
}

