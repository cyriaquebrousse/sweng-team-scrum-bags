package ch.epfl.scrumtool.gui.utils;

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
import ch.epfl.scrumtool.network.ScrumClient;

public abstract class MockClient implements ScrumClient {

    @Override
    public void deleteUser(User user, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void updateUser(User user, User ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadProjects(Callback<List<Project>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void insertProject(Project project, Callback<Project> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void updateProject(Project project, Project ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void deleteProject(Project project, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadPlayers(Project project, Callback<List<Player>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void addPlayer(Player player, Project project, Callback<Player> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void updatePlayer(Player player, Player ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void removePlayer(Player player, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void addPlayerToProject(Project project, String userEmail, Role role, Callback<Player> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadBacklog(Project project, Callback<List<MainTask>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void insertMainTask(MainTask task, Project project, Callback<MainTask> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void updateMainTask(MainTask task, MainTask ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void deleteMainTask(MainTask task, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadIssues(MainTask task, Callback<List<Issue>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadIssues(Sprint sprint, Callback<List<Issue>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void insertIssue(Issue issue, MainTask task, Callback<Issue> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void addIssueToSprint(Issue issue, Sprint sprint, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void removeIssueFromSprint(Issue issue, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void updateIssue(Issue issue, Issue ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void deleteIssue(Issue issue, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadUnsprintedIssues(Project project, Callback<List<Issue>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadIssuesForUser(User user, Callback<List<TaskIssueProject>> cB) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void loadSprints(Project project, Callback<List<Sprint>> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void insertSprint(Sprint sprint, Project project, Callback<Sprint> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void updateSprint(Sprint sprint, Sprint ref, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

    @Override
    public void deleteSprint(Sprint sprint, Callback<Boolean> callback) {
        throw new UnsupportedOperationException("Not Implemented");

    }

}
