/**
 * 
 */
package ch.epfl.scrumtool.database;

import java.util.List;

import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * Defines all database operations related the issues
 * 
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
     * Load the issues that are not assigned to a Sprint from the datastore
     * 
     * @param project
     * @param callback
     */
    void loadUnsprintedIssues(final Project project, final Callback<List<Issue>> callback);

    /**
     * Insert an Issue in the datastore
     * 
     * @param issue
     * @param mainTask
     * @param cB
     */
    void insert(final Issue issue, final MainTask mainTask, final Callback<Issue> cB);

    /**
     * Add an Issue to a Sprint
     * 
     * @param issue
     * @param sprint
     * @param cB
     */
    void assignIssueToSprint(final Issue issue, final Sprint sprint,
            final Callback<Void> cB);

    
    /**
     * Loads a list of issues which don't have the state finished for a selected user
     * @param user
     * @param cB
     */
    
    void loadIssuesForUser(final User user, final Callback<List<TaskIssueProject>> cB);
}
