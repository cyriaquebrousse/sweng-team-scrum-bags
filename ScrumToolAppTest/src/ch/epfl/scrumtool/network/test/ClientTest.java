package ch.epfl.scrumtool.network.test;

import java.util.List;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.ScrumClient;
/**
 * 
 * @author sylb
 *
 */
public class ClientTest extends TestCase {

    public void testGetSetScrumClient() {
        ScrumClient client = new ScrumClient() {
            
            @Override
            public void updateUser(User user, Callback<Void> callback) { }
            @Override
            public void updateSprint(Sprint sprint, Callback<Void> callback) { }
            @Override
            public void updateProject(Project project, Callback<Void> callback) { }
            @Override
            public void updatePlayer(Player player, Callback<Void> callback) { }
            @Override
            public void updateMainTask(MainTask task, Callback<Void> callback) { }
            @Override
            public void updateIssue(Issue issue, Callback<Void> callback) { }
            @Override
            public void removePlayer(Player player, Callback<Void> callback) { }
            @Override
            public void loadUnsprintedIssues(Project project, Callback<List<Issue>> callback) { }
            @Override
            public void loadSprints(Project project, Callback<List<Sprint>> callback) { }
            @Override
            public void loadProjects(Callback<List<Project>> callback) { }
            @Override
            public void loadPlayers(Project project, Callback<List<Player>> callback) { }
            @Override
            public void loadIssuesForUser(User user, Callback<List<TaskIssueProject>> cB) { }
            @Override
            public void loadIssues(Sprint sprint, Callback<List<Issue>> callback) { }
            @Override
            public void loadIssues(MainTask task, Callback<List<Issue>> callback) { }
            @Override
            public void loadInvitedPlayers(Callback<List<Player>> callback) { }
            @Override
            public void loadBacklog(Project project, Callback<List<MainTask>> callback) { }
            @Override
            public void insertSprint(Sprint sprint, Project project, Callback<Sprint> callback) { }
            @Override
            public void insertProject(Project project, Callback<Project> callback) { }
            @Override
            public void insertMainTask(MainTask task, Project project, Callback<MainTask> callback) { }
            @Override
            public void insertIssue(Issue issue, MainTask task, Callback<Issue> callback) { }
            @Override
            public void deleteUser(User user, Callback<Void> callback) { }
            @Override
            public void deleteSprint(Sprint sprint, Callback<Void> callback) { }
            @Override
            public void deleteProject(Project project, Callback<Void> callback) { }
            @Override
            public void deleteMainTask(MainTask task, Callback<Void> callback) { }
            @Override
            public void deleteIssue(Issue issue, Callback<Void> callback) { }
            @Override
            public void addPlayerToProject(Project project, String userEmail,
                    Role role, Callback<Player> callback) { }
            @Override
            public void addIssueToSprint(Issue issue, Sprint sprint, Callback<Void> callback) { }
            @Override
            public void setPlayerAsAdmin(Player player, Callback<Void> callback) { }
        };
        
        Client.setScrumClient(client);
        assertEquals(client, Client.getScrumClient());
    }
    
    public void testGetSetNullScrumClient() {
        Client.setScrumClient(null);
        assertEquals(null, Client.getScrumClient());
    }
}
