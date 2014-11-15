/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DBHandlers;
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
public class DBScrumClient implements ScrumClient {
    private final DBHandlers databaseHandlers;

    public DBScrumClient(DBHandlers dbHandlers) {
        this.databaseHandlers = dbHandlers;
    }

    @Override
    public void removeUser(String userKey, Callback<Boolean> callback) {
        databaseHandlers.getUserHandler().remove(userKey, callback);
    }

    @Override
    public void updateUser(User user, User ref, Callback<Boolean> callback) {
        databaseHandlers.getUserHandler().update(user, ref, callback);
    }

    @Override
    public void loadProjects(Callback<List<Project>> callback) {
        databaseHandlers.getProjectHandler().loadProjects(callback);
    }

    @Override
    public void insertProject(Project project, Callback<Project> callback) {
        databaseHandlers.getProjectHandler().insert(project, callback);
    }

    @Override
    public void updateProject(Project project, Project ref,
            Callback<Boolean> callback) {
        databaseHandlers.getProjectHandler().update(project, project, callback);
    }

    @Override
    public void removeProject(String projectKey, Callback<Boolean> callback) {
        databaseHandlers.getProjectHandler().remove(projectKey, callback);
    }

    @Override
    public void loadPlayers(String projectKey, Callback<List<Player>> callback) {
        databaseHandlers.getPlayerHandler().loadPlayers(projectKey, callback);
    }

    @Override
    public void removePlayer(String playerKey, Callback<Boolean> callback) {
        databaseHandlers.getPlayerHandler().remove(playerKey, callback);
    }

    @Override
    public void addPlayerToProject(String projectKey, String userEmail,
            Role role, Callback<Player> callback) {
        databaseHandlers.getPlayerHandler().addPlayerToProject(projectKey,
                userEmail, role, callback);
    }

    @Override
    public void loadBacklog(String projectKey, Callback<List<MainTask>> callback) {
        databaseHandlers.getMainTaskHandler().loadMainTasks(projectKey,
                callback);
    }

    @Override
    public void insertMainTask(MainTask task, String projectKey,
            Callback<MainTask> callback) {
        databaseHandlers.getMainTaskHandler().insert(task, projectKey, callback);
    }

    @Override
    public void updateMainTask(MainTask task, MainTask ref, String project, Callback<Boolean> callback) {
        databaseHandlers.getMainTaskHandler().update(task, ref, project, callback);
    }

    @Override
    public void removeMainTask(String taskKey, Callback<Boolean> callback) {
        databaseHandlers.getMainTaskHandler().remove(taskKey, callback);
    }

    @Override
    public void loadIssuesFromMaintask(String taskKey,
            Callback<List<Issue>> callback) {
        databaseHandlers.getIssueHandler().loadIssuesFromMaintask(taskKey,
                callback);
    }

    @Override
    public void loadIssuesFromSprint(String sprintKey,
            Callback<List<Issue>> callback) {
        databaseHandlers.getIssueHandler().loadIssuesFromSprint(sprintKey,
                callback);
    }

    @Override
    public void insertIssue(Issue issue, String taskKey,
            Callback<Issue> callback) {
        databaseHandlers.getIssueHandler().insert(issue, taskKey, callback);
    }

    @Override
    public void addIssueToSprint(Issue issue, String sprintKey,
            Callback<Boolean> callback) {
        databaseHandlers.getIssueHandler().assignIssueToSprint(issue,
                sprintKey, callback);
    }

    @Override
    public void removeIssueFromSprint(String issue, String sprintKey,
            Callback<Boolean> callback) {
        databaseHandlers.getIssueHandler().removeIssueFromSprint(issue, sprintKey,
                callback);
    }

    @Override
    public void updateIssue(Issue issue, Issue ref, MainTask mainTask,
            Callback<Boolean> callback) {
        databaseHandlers.getIssueHandler().update(issue, ref, callback);
    }

    @Override
    public void removeIssue(String issueKey, Callback<Boolean> callback) {
        databaseHandlers.getIssueHandler().remove(issueKey, callback);
    }

    @Override
    public void loadSprints(String projectKey, Callback<List<Sprint>> callback) {
        databaseHandlers.getSprintHandler().loadSprints(projectKey, callback);
    }

    @Override
    public void insertSprint(Sprint sprint, String projectKey,
            Callback<Sprint> callback) {
        databaseHandlers.getSprintHandler()
                .insert(sprint, projectKey, callback);
    }

    @Override
    public void updateSprint(Sprint sprint, Sprint ref,
            Callback<Boolean> callback) {
        databaseHandlers.getSprintHandler().update(sprint, ref, callback);
    }

    @Override
    public void removeSprint(String sprintKey, Callback<Boolean> callback) {
        databaseHandlers.getSprintHandler().remove(sprintKey, callback);
    }

}
