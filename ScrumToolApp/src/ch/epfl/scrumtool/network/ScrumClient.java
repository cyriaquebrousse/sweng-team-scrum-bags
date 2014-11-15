/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 *
 */
public interface ScrumClient {
    
    // User methods
    void removeUser(final String userKey, final Callback<Boolean> callback);
    void updateUser(final User user, final User ref, final Callback<Boolean> callback);
    
    // Project methods
    void loadProjects(final Callback<List<Project>> callback);
    void insertProject(final Project project, final Callback<Project> callback);
    void updateProject(final Project project, final Project ref, final Callback<Boolean> callback);
    void removeProject(final String projectKey, final Callback<Boolean> callback);
    
    // Player methods
    void loadPlayers(final String projectKey, final Callback<List<Player>> callback);
    void removePlayer(final String playerKey, final Callback<Boolean> callback);
    void addPlayerToProject(final String projectKey, final String userEmail, final Role role, final Callback<Player> callback);
    
    // Maintask methods
    void loadBacklog(final String projectKey, final Callback<List<MainTask>> callback);
    void insertMainTask(final MainTask task, final String projectKey, final Callback<MainTask> callback);
    void updateMainTask(final MainTask task, final MainTask ref, final String projectKey, final Callback<Boolean> callback);
    void removeMainTask(final String taskKey, final Callback<Boolean> callback);
    
    // Issue methods
    void loadIssuesFromMaintask(final String taskKey, final Callback<List<Issue>> callback);
    void loadIssuesFromSprint(final String sprintKey, final Callback<List<Issue>> callback);
    void insertIssue(final Issue issue, final String taskKey, final Callback<Issue> callback);
    void addIssueToSprint(final Issue issue, final String sprintKey, final Callback<Boolean> callback);
    void removeIssueFromSprint(final String issue, final String sprintKey, final Callback<Boolean> callback);
    void updateIssue(final Issue issue, final Issue ref, final MainTask mainTask, Callback<Boolean> callback);
    void removeIssue(final String issueKey, final Callback<Boolean> callback);
     
    // Sprint methods
    void loadSprints(final String projectKey, final Callback<List<Sprint>> callback);
    void insertSprint(final Sprint sprint, final String projectKey, final Callback<Sprint> callback);
    void updateSprint(final Sprint sprint, final Sprint ref, final Callback<Boolean> callback);
    void removeSprint(final String sprintKey, final Callback<Boolean> callback);
}
