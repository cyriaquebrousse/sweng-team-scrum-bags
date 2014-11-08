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
    void loadMainTasks(final Project project, Callback<List<MainTask>> cB);
    void insert(final MainTask object, final Project project, final Callback<String> dbC);
}
