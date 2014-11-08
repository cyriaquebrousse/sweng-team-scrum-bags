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
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 *
 */
public interface ScrumClient {
    void deleteUser(final User user, final Callback<Boolean> cB);
    void updateUser(final User user, final User ref, final Callback<Boolean> cB);
    
    void loadProjects(final Callback<List<Project>> cB);
    void insertProject(final Project project, final Callback<String> cB);
    void updateProject(final Project project, final Project ref, final Callback<Boolean> cB);
    void deleteProject(final Project project, final Callback<Boolean> cB);
    
    void loadPlayers(final Project project, final Callback<List<Player>> cB);
    void addPlayer(final Player player, final Project project, final Callback<String> cB);
    void removePlayer(final Player player, final Callback<Boolean> cB);
    
    void loadBacklog(final Project project, final Callback<List<MainTask>> cB);
    void insertMainTask(final MainTask task, final Project project, final Callback<String> cB);
    void updateMainTask(final MainTask task, final MainTask ref, final Callback<Boolean> cB);
    void deleteMainTask(final MainTask task, final Callback<Boolean> cB);
    
    void loadIssues(final MainTask task, final Callback<List<Issue>> cB);
    void loadIssues(final Sprint sprint, final Callback<List<Issue>> cB);
    void insertIssue(final MainTask task, final Issue issue, final Callback<String> cB);
    void addIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> cB);
    void removeIssue(final Issue issue, final Sprint sprint, final Callback<Boolean> cB);
    void updateIssue(final Issue issue, final Issue reference, Callback<Boolean> cB);
    void deleteIssue(final Issue issue, final Callback<Boolean> cB);
    
    void loadSprints(final Project project, final Callback<List<Sprint>> cB);
    void insertSprint(final Sprint sprint, final Project project, final Callback<String> cB);
    void updateSprint(final Sprint sprint, final Sprint reference, final Callback<Boolean> cB);
    void deleteSprint(final Sprint sprint, final Callback<Boolean> cB);
}
