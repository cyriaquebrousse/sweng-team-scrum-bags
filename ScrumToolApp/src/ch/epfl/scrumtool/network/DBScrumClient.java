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
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 * 
 */
public class DBScrumClient implements ScrumClient {
    private final DBHandlers dbH;

    public DBScrumClient(DBHandlers dbH) {
        this.dbH = dbH;
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#deleteUser(ch.epfl.scrumtool.entity.User, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void deleteUser(User user, Callback<Boolean> cB) {
        dbH.getUserHandler().remove(user, cB);
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#updateUser(ch.epfl.scrumtool.entity.User, ch.epfl.scrumtool.entity.User, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void updateUser(User user, User ref, Callback<Boolean> cB) {
       dbH.getUserHandler().update(user, ref, cB);
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadProjects(ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadProjects(Callback<List<Project>> cB) {
        dbH.getProjectHandler().loadProjects(cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#insertProject(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void insertProject(Project project, Callback<Project> cB) {
        dbH.getProjectHandler().insert(project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#updateProject(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void updateProject(Project project, Project ref, Callback<Boolean> cB) {
        dbH.getProjectHandler().update(project, ref, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#deleteProject(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void deleteProject(Project project, Callback<Boolean> cB) {
       dbH.getProjectHandler().remove(project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadBacklog(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadBacklog(Project project, Callback<List<MainTask>> cB) {
       dbH.getMainTaskHandler().loadMainTasks(project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#insertMainTask(ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void insertMainTask(MainTask task, Project project,
            Callback<MainTask> cB) {
        dbH.getMainTaskHandler().insert(task, project, cB);        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#updateMainTask(ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void updateMainTask(MainTask task, MainTask ref, Callback<Boolean> cB) {
        dbH.getMainTaskHandler().update(task,ref , cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#deleteMainTask(ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void deleteMainTask(MainTask task, Callback<Boolean> cB) {
        dbH.getMainTaskHandler().remove(task, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadIssues(ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadIssues(MainTask task, Callback<List<Issue>> cB) {
        dbH.getIssueHandler().loadIssues(task, cB);    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadIssues(ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadIssues(Sprint sprint, Callback<List<Issue>> cB) {
        dbH.getIssueHandler().loadIssues(sprint, cB);
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#insertIssue(ch.epfl.scrumtool.entity.MainTask, ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void insertIssue(MainTask task, Issue issue, Callback<Issue> cB) {
        dbH.getIssueHandler().insert(issue, task, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#addIssue(ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void addIssue(Issue issue, Sprint sprint, Callback<Boolean> cB) {
        dbH.getIssueHandler().assignIssueToSprint(issue, sprint, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#removeIssue(ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void removeIssue(Issue issue, Sprint sprint, Callback<Boolean> cB) {
        dbH.getIssueHandler().removeIssue(issue, sprint, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#updateIssue(ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void updateIssue(Issue issue, Issue reference, Callback<Boolean> cB) {
        dbH.getIssueHandler().update(issue, reference, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#deleteIssue(ch.epfl.scrumtool.entity.Issue, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void deleteIssue(Issue issue, Callback<Boolean> cB) {
        dbH.getIssueHandler().remove(issue, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadSprints(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadSprints(Project project, Callback<List<Sprint>> cB) {
        dbH.getSprintHandler().loadSprints(project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#insertSprint(ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void insertSprint(Sprint sprint, Project project, Callback<Sprint> cB) {
        dbH.getSprintHandler().insert(sprint, project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#updateSprint(ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void updateSprint(Sprint sprint, Sprint reference,
            Callback<Boolean> cB) {
        dbH.getSprintHandler().update(sprint, reference, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#deleteSprint(ch.epfl.scrumtool.entity.Sprint, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void deleteSprint(Sprint sprint, Callback<Boolean> cB) {
       dbH.getSprintHandler().remove(sprint, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#loadPlayers(ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void loadPlayers(Project project, Callback<List<Player>> cB) {
        dbH.getPlayerHandler().loadPlayers(project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#addPlayer(ch.epfl.scrumtool.entity.Player, ch.epfl.scrumtool.entity.Project, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void addPlayer(Player player, Project project, Callback<Player> cB) {
        dbH.getPlayerHandler().insert(player, project, cB);
        
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.network.ScrumClient#removePlayer(ch.epfl.scrumtool.entity.Player, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void removePlayer(Player player, Callback<Boolean> cB) {
        dbH.getPlayerHandler().remove(player, cB);
        
    }
}
