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
    void deleteUser(final User user, final Callback<Boolean> callback);
    void updateUser(final User user, final User ref, final Callback<Boolean> callback);
    
    // Project methods
    void loadProjects(final Callback<List<Project>> callback);
    void insertProject(final Project project, final Callback<Project> callback);
    void updateProject(final Project project, final Project ref, final Callback<Boolean> callback);
    void deleteProject(final Project project, final Callback<Boolean> callback);
    
    // Player methods
    void loadPlayers(final Project project, final Callback<List<Player>> callback);
    void addPlayer(final Player player, final Project project, final Callback<Player> callback);
    void updatePlayer(final Player player, final Player ref, final Callback<Boolean> callback);
    void removePlayer(final Player player, final Callback<Boolean> callback);
    void addPlayerToProject(final Project project, final String userEmail, final Role role, final Callback<Player> callback);
    
    // Maintask methods
    void loadBacklog(final Project project, final Callback<List<MainTask>> callback);
    void insertMainTask(final MainTask task, final Project project, final Callback<MainTask> callback);
    void updateMainTask(final MainTask task, final MainTask ref, final Callback<Boolean> callback);
    void deleteMainTask(final MainTask task, final Callback<Boolean> callback);
    
    // Issue methods
    void loadIssues(final MainTask task, final Callback<List<Issue>> callback);
    void loadIssues(final Sprint sprint, final Callback<List<Issue>> callback);
    void insertIssue(final Issue issue, final MainTask task, final Callback<Issue> callback);
    void addIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> callback);
    void removeIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> callback);
    void updateIssue(final Issue issue, final Issue ref, Callback<Boolean> callback);
    void deleteIssue(final Issue issue, final Callback<Boolean> callback);
     
    // Sprint methods
    void loadSprints(final Project project, final Callback<List<Sprint>> callback);
    void insertSprint(final Sprint sprint, final Project project, final Callback<Sprint> callback);
    void updateSprint(final Sprint sprint, final Sprint ref, final Callback<Boolean> callback);
    void deleteSprint(final Sprint sprint, final Callback<Boolean> callback);
}
