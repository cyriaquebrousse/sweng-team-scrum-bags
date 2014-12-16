package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandlers;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * Class putting together all important database operations to provide easy access
 * 
 * @author aschneuw
 */
public class DatabaseScrumClient implements ScrumClient {
    private final DatabaseHandlers databaseHandlers;

    public DatabaseScrumClient(DatabaseHandlers dbHandlers) {
        this.databaseHandlers = dbHandlers;
    }

    @Override
    public void deleteUser(User user, Callback<Void> callback) {
        databaseHandlers.getUserHandler().remove(user, callback);
    }

    @Override
    public void updateUser(User user, Callback<Void> callback) {
        databaseHandlers.getUserHandler().update(user, callback);
    }

    @Override
    public void loadProjects(Callback<List<Project>> callback) {
        databaseHandlers.getProjectHandler().loadProjects(callback);

    }

    @Override
    public void insertProject(Project project, Callback<Project> callback) {
        databaseHandlers.getProjectHandler().insert(project, callback);
    }


    public void updateProject(Project project, Callback<Void> callback) {
        databaseHandlers.getProjectHandler().update(project, callback);
    }

    @Override
    public void deleteProject(Project project, Callback<Void> callback) {
        databaseHandlers.getProjectHandler().remove(project, callback);
    }

    @Override
    public void loadBacklog(Project project, Callback<List<MainTask>> callback) {
        databaseHandlers.getMainTaskHandler().loadMainTasks(project, callback);
    }

    @Override
    public void insertMainTask(MainTask task, Project project,
            Callback<MainTask> callback) {
        databaseHandlers.getMainTaskHandler().insert(task, project, callback);
    }

    @Override
    public void updateMainTask(MainTask task, Callback<Void> callback) {
        databaseHandlers.getMainTaskHandler().update(task, callback);
    }

    @Override
    public void deleteMainTask(MainTask task, Callback<Void> callback) {
        databaseHandlers.getMainTaskHandler().remove(task, callback);
    }
    

    @Override
    public void loadIssues(MainTask task, Callback<List<Issue>> callback) {
        databaseHandlers.getIssueHandler().loadIssues(task, callback);
    }

    @Override
    public void loadIssues(Sprint sprint, Callback<List<Issue>> callback) {
        databaseHandlers.getIssueHandler().loadIssues(sprint, callback);
    }

    @Override
    public void insertIssue(Issue issue, MainTask task, Callback<Issue> callback) {
        databaseHandlers.getIssueHandler().insert(issue, task, callback);
    }

    @Override
    public void addIssueToSprint(Issue issue, Sprint sprint, Callback<Void> callback) {
        databaseHandlers.getIssueHandler().assignIssueToSprint(issue, sprint,
                callback);
    }

    @Override
    public void updateIssue(Issue issue, Callback<Void> callback) {
        databaseHandlers.getIssueHandler().update(issue, callback);
    }

    @Override
    public void deleteIssue(Issue issue, Callback<Void> callback) {
        databaseHandlers.getIssueHandler().remove(issue, callback);
    }

    @Override
    public void loadSprints(Project project, Callback<List<Sprint>> callback) {
        databaseHandlers.getSprintHandler().loadSprints(project, callback);
    }

    @Override
    public void insertSprint(Sprint sprint, Project project,
            Callback<Sprint> callback) {
        databaseHandlers.getSprintHandler().insert(sprint, project, callback);
    }

    @Override
    public void updateSprint(Sprint sprint, Callback<Void> callback) {
        databaseHandlers.getSprintHandler().update(sprint, callback);
    }

    @Override
    public void deleteSprint(Sprint sprint, Callback<Void> callback) {
        databaseHandlers.getSprintHandler().remove(sprint, callback);
    }

    @Override
    public void loadPlayers(Project project, Callback<List<Player>> callback) {
        databaseHandlers.getPlayerHandler().loadPlayers(project, callback);
    }
    
    @Override
    public void updatePlayer(Player player, Callback<Void> callback) {
        databaseHandlers.getPlayerHandler().update(player, callback);
    }

    @Override
    public void removePlayer(Player player, Callback<Void> callback) {
        databaseHandlers.getPlayerHandler().remove(player, callback);
    }

    @Override
    public void addPlayerToProject(Project project, String userEmail,
            Role role, Callback<Player> callback) {
        databaseHandlers.getPlayerHandler().addPlayerToProject(project,
                userEmail, role, callback);
    }

    @Override
    public void loadUnsprintedIssues(Project project,
            Callback<List<Issue>> callback) {
        databaseHandlers.getIssueHandler().loadUnsprintedIssues(project, callback);
    }

    @Override
    public void loadIssuesForUser(User user, Callback<List<TaskIssueProject>> cB) {
        databaseHandlers.getIssueHandler().loadIssuesForUser(user, cB);
    }

    @Override
    public void loadInvitedPlayers(Callback<List<Player>> callback) {
        databaseHandlers.getPlayerHandler().loadInvitedPlayers(callback);
    }

    @Override
    public void setPlayerAsAdmin(Player player, Callback<Void> callback) {
        databaseHandlers.getPlayerHandler().setPlayerAsAdmin(player, callback);
    }
}