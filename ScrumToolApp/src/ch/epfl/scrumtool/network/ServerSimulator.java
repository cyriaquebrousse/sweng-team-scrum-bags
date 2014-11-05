/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.Arrays;
import java.util.List;

import ch.epfl.scrumtool.entity.Entity;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;

/**
 * @author ketsio
 *
 */
public final class ServerSimulator {
    
    public final static List<User> USERS = Arrays.asList(
            Entity.JOHN_SMITH,
            Entity.MARIA_LINDA,
            Entity.CYRIAQUE_BROUSSE,
            Entity.LORIS_LEIVA,
            Entity.ARJEN_LENSTRA,
            Entity.MICHAEL_SCOFIELD);
    
    public final static List<Project> PROJECTS = Arrays.asList(
            Entity.COOL_PROJECT,
            Entity.SUPER_PROJECT);
    
    public final static List<MainTask> TASKS = Arrays.asList(
            Entity.TASK_A,
            Entity.TASK_B,
            Entity.TASK_C,
            Entity.TASK_D,
            Entity.TASK_E);
    
    public final static List<Issue> ISSUES = Arrays.asList(
            Entity.ISSUE_A1,
            Entity.ISSUE_A2,
            Entity.ISSUE_A3,
            Entity.ISSUE_B1,
            Entity.ISSUE_B2,
            Entity.ISSUE_C1,
            Entity.ISSUE_D1,
            Entity.ISSUE_D2,
            Entity.ISSUE_E1);
    
    
    
    public static User getUserById(long id) {
        return USERS.get((int) id);
    }
    
    public static Project getProjectById(long id) {
        return PROJECTS.get((int) id);
    }
    
    public static MainTask getTaskById(long id) {
        return TASKS.get((int) id);
    }
    
    public static Issue getIssueById(long id) {
        return ISSUES.get((int) id);
    }

    public static List<MainTask> getBacklogByProjectId(long projectId) {
//        return new ArrayList<>(PROJECTS.get((int) projectId).getBacklog());
        return TASKS;
    }
}
