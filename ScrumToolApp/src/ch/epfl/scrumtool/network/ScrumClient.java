package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 */
public interface ScrumClient {
    
    // User methods
    /**
     * @param user
     * @param callback
     */
    void deleteUser(final User user, final Callback<Void> callback);
    /**
     * @param user
     * @param ref
     * @param callback
     */
    void updateUser(final User user, final Callback<Void> callback);
    
    // Project methods
    /**
     * @param callback
     */
    void loadProjects(final Callback<List<Project>> callback);
    /**
     * @param project
     * @param callback
     */
    void insertProject(final Project project, final Callback<Project> callback);
    /**
     * @param project
     * @param ref
     * @param callback
     */
    void updateProject(final Project project, final Callback<Void> callback);
    /**
     * @param project
     * @param callback
     */
    void deleteProject(final Project project, final Callback<Void> callback);
    
    // Player methods
    /**
     * @param project
     * @param callback
     */
    void loadPlayers(final Project project, final Callback<List<Player>> callback);
    
    /**
     * @param callback
     */
    void loadInvitedPlayers(final Callback<List<Player>> callback);
    /**
     * @param player
     * @param project
     * @param callback
     */
    void addPlayer(final Player player, final Project project, final Callback<Player> callback);
    /**
     * @param player
     * @param ref
     * @param callback
     */
    void updatePlayer(final Player player, final Callback<Void> callback);
    /**
     * @param player
     * @param callback
     */
    void removePlayer(final Player player, final Callback<Void> callback);
    /**
     * @param project
     * @param userEmail
     * @param role
     * @param callback
     */
    void addPlayerToProject(final Project project, final String userEmail, 
            final Role role, final Callback<Player> callback);
    
    // Maintask methods
    /**
     * @param project
     * @param callback
     */
    void loadBacklog(final Project project, final Callback<List<MainTask>> callback);
    /**
     * @param task
     * @param project
     * @param callback
     */
    void insertMainTask(final MainTask task, final Project project, final Callback<MainTask> callback);
    /**
     * @param task
     * @param ref
     * @param callback
     */
    void updateMainTask(final MainTask task, final Callback<Void> callback);
    /**
     * @param task
     * @param callback
     */
    void deleteMainTask(final MainTask task, final Callback<Void> callback);
    
    // Issue methods
    /**
     * @param task
     * @param callback
     */
    void loadIssues(final MainTask task, final Callback<List<Issue>> callback);
    /**
     * @param sprint
     * @param callback
     */
    void loadIssues(final Sprint sprint, final Callback<List<Issue>> callback);
    /**
     * @param issue
     * @param task
     * @param callback
     */
    void insertIssue(final Issue issue, final MainTask task, final Callback<Issue> callback);
    /**
     * @param issue
     * @param sprint
     * @param callback
     */
    void addIssueToSprint(final Issue issue, final Sprint sprint, final Callback<Void> callback);

    /**
     * @param issue
     * @param ref
     * @param callback
     */
    void updateIssue(final Issue issue, Callback<Void> callback);
    /**
     * @param issue
     * @param callback
     */
    void deleteIssue(final Issue issue, final Callback<Void> callback);
    /**
     * @param project
     * @param callback
     */
    void loadUnsprintedIssues(final Project project, final Callback<List<Issue>> callback);
    /**
     * @param user
     * @param cB
     */
    void loadIssuesForUser(final User user, final Callback<List<TaskIssueProject>> cB);
     
    // Sprint methods
    /**
     * @param project
     * @param callback
     */
    void loadSprints(final Project project, final Callback<List<Sprint>> callback);
    /**
     * @param sprint
     * @param project
     * @param callback
     */
    void insertSprint(final Sprint sprint, final Project project, final Callback<Sprint> callback);
    /**
     * @param sprint
     * @param ref
     * @param callback
     */
    void updateSprint(final Sprint sprint, final Callback<Void> callback);
    /**
     * @param sprint
     * @param callback
     */
    void deleteSprint(final Sprint sprint, final Callback<Void> callback);
}
