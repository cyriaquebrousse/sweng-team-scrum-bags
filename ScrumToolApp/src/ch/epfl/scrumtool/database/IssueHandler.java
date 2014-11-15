/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;

/**
 * @author aschneuw
 * 
 */
public interface IssueHandler extends DatabaseHandler<Issue> {

    /**
     * Load the issues of a MainTask from the datastore
     * 
     * @param sprintKey
     * @param cB
     */
    void loadIssuesFromMaintask(final String maintaskKey,
            final Callback<List<Issue>> cB);

    /**
     * Insert an Issue in the datastore
     * 
     * @param issue
     * @param taskKey
     * @param cB
     */
    void insert(final Issue issue, final String taskKey,
            final Callback<Issue> cB);

    /**
     * Add an Issue to a Sprint
     * 
     * @param issue
     * @param sprintKey
     * @param cB
     */
    void assignIssueToSprint(final Issue issue, final String sprintKey,
            final Callback<Boolean> cB);

    /**
     * Remove the Issue from the a Sprint
     * 
     * @param issue
     * @param sprintKey
     * @param cB
     */
    void removeIssueFromSprint(String issue, String sprint, Callback<Boolean> cB);

    /**
     * Updates an issue in the database
     * 
     * @param issue
     * @param ref
     * @param mainTask
     * @param cb
     */
    void update(final Issue issue, final Issue ref, final MainTask mainTask,
            final Callback<Boolean> cb);

    /**
     * Loads the issues from a Sprint in the database
     * 
     * @param sprintKey
     * @param callback
     */
    void loadIssuesFromSprint(String sprintKey, Callback<List<Issue>> callback);

}
