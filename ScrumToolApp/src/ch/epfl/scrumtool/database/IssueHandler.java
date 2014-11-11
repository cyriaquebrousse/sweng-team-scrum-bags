/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author aschneuw
 * 
 */
public interface IssueHandler extends DatabaseHandler<Issue> {

    /**
     * Load the issues of a MainTask from the datastore
     * 
     * @param mainTask
     * @param cB
     */
    void loadIssues(final MainTask mainTask, final Callback<List<Issue>> cB);

    /**
     * Load the issues of a Sprint from the datastore
     * 
     * @param sprint
     * @param cB
     */
    void loadIssues(final Sprint sprint, final Callback<List<Issue>> cB);

    /**
     * Insert an Issue in the datastore
     * 
     * @param issue
     * @param mainTask
     * @param cB
     */
    void insert(final Issue issue, final MainTask mainTask,
            final Callback<Issue> cB);

    /**
     * Add an Issue to a Sprint
     * 
     * @param issue
     * @param sprint
     * @param cB
     */
    void assignIssueToSprint(final Issue issue, final Sprint sprint,
            final Callback<Boolean> cB);

    /**
     * Remove the Issue from the a Sprint
     * 
     * @param issue
     * @param sprint
     * @param cB
     */
    void removeIssue(final Issue issue, final Sprint sprint,
            final Callback<Boolean> cB);

}
