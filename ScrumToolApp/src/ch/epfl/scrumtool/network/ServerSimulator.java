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
    
    private static List<User> users = Arrays.asList(
            Entity.JOHN_SMITH,
            Entity.MARIA_LINDA,
            Entity.CYRIAQUE_BROUSSE,
            Entity.LORIS_LEIVA,
            Entity.ARJEN_LENSTRA,
            Entity.MICHAEL_SCOFIELD);
    
    private static List<Project> projects = Arrays.asList(
            Entity.COOL_PROJECT,
            Entity.SUPER_PROJECT);
    
    private static List<MainTask> tasks = Arrays.asList(
            Entity.TASK_A,
            Entity.TASK_B,
            Entity.TASK_C,
            Entity.TASK_D,
            Entity.TASK_E);
    
    private static List<Issue> issues = Arrays.asList(
            Entity.ISSUE_A1,
            Entity.ISSUE_A2,
            Entity.ISSUE_A3,
            Entity.ISSUE_B1,
            Entity.ISSUE_B2,
            Entity.ISSUE_C1,
            Entity.ISSUE_D1,
            Entity.ISSUE_D2,
            Entity.ISSUE_E1);
    
    
    
    public static User getUserById(int id) {
        return users.get(id);
    }
    
    public static Project getProjectById(int id) {
        return projects.get(id);
    }
    
    public static MainTask getTaskById(int id) {
        return tasks.get(id);
    }
    
    public static Issue getIssueById(int id) {
        return issues.get(id);
    }
}
