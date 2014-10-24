/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/**
 * @author ketsio
 * 
 */
public final class Entity {
    
    private static final Random RAND = new Random();
    
    // Issues
    public static final IssueInterface ISSUE_A1 = createIssue("Create the profile", Status.READY_FOR_SPRINT, 3);
    public static final IssueInterface ISSUE_A2 = createIssue("Create the project", Status.IN_SPRINT, 2);
    public static final IssueInterface ISSUE_A3 = createIssue("Create the task", Status.READY_FOR_ESTIMATION, 0);
    public static final IssueInterface ISSUE_B1 = createIssue("Take an empty cup", Status.IN_SPRINT, 0.5f);
    public static final IssueInterface ISSUE_B2 = createIssue("Put the coffe on it", Status.IN_SPRINT, 0.3f);
    public static final IssueInterface ISSUE_C1 = createIssue("Call Google", Status.IN_SPRINT, 10);
    public static final IssueInterface ISSUE_D1 = createIssue("Why ?", Status.FINISHED, 100);
    public static final IssueInterface ISSUE_D2 = createIssue("Why not ?", Status.FINISHED, 0.5f);
    public static final IssueInterface ISSUE_E1 = createIssue("Find time to sleep", Status.READY_FOR_ESTIMATION, 2);
    

    // Tasks
    public static final TaskInterface TASK_A = createTask("Create Mock UI", Status.IN_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_A1, ISSUE_A2, ISSUE_A3)));
    public static final TaskInterface TASK_B = createTask("Take a Coffee", Status.READY_FOR_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_B1, ISSUE_B2)));
    public static final TaskInterface TASK_C = createTask("Implement the Google App Engine server", Status.IN_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_C1)));
    public static final TaskInterface TASK_D = createTask("Find the meaning of the univers", Status.FINISHED,
            new HashSet<>(Arrays.asList(ISSUE_D1, ISSUE_D2)));
    public static final TaskInterface TASK_E = createTask("Having a good night sleep", Status.READY_FOR_ESTIMATION,
            new HashSet<>(Arrays.asList(ISSUE_E1)));
    
    // Users
    public static final UserInterface JOHN_SMITH = createUser("John Smith");
    public static final UserInterface MARIA_LINDA = createUser("Maria Linda");
    public static final UserInterface CYRIAQUE_BROUSSE = createUser("Cyriaque Brousse");
    public static final UserInterface LORIS_LEIVA = createUser("Loris Leiva");
    public static final UserInterface ARJEN_LENSTRA = createUser("Arjen Lenstra");
    public static final UserInterface MICHAEL_SCOFIELD = createUser("Michael Scofield");
    
    // Connected user
    public static final UserInterface CONNECTECT_USER = MARIA_LINDA;

    // Projects
    public static final ProjectInterface COOL_PROJECT = createProject("Cool Project",
            new HashSet<>(Arrays.asList(JOHN_SMITH, MARIA_LINDA, CYRIAQUE_BROUSSE, CYRIAQUE_BROUSSE)),
            new HashSet<>(Arrays.asList(TASK_A, TASK_B)));
    
    public static final ProjectInterface SUPER_PROJECT = createProject("Super Project",
            new HashSet<>(Arrays.asList(JOHN_SMITH, MARIA_LINDA, ARJEN_LENSTRA, MICHAEL_SCOFIELD)),
            new HashSet<>(Arrays.asList(TASK_C, TASK_D)));
    


    /**
     * @param string
     * @param readyForEstimation
     * @return
     */
    private static IssueInterface createIssue(String name, Status status, float estimation) {
        String description = "This is the description of the issue called + \"" + name + "\"";
        IssueInterface issue = new Issue(name, description, estimation, null, status);
        return issue;
    }

    /**
     * @param string
     * @return
     */
    private static TaskInterface createTask(String name, Status status, Set<IssueInterface> issues) {
        String description = "This is the description of the task called + \"" + name + "\"";
        TaskInterface task = new Task(name, description, issues, status, Priority.NORMAL);
        return task;
    }


    private static ProjectInterface createProject(String name, Set<UserInterface> users, Set<TaskInterface> backlog) {
        String description = "This is a description for the project called \"";
        description += name + "\". This project is one of the best you'll ever ";
        description += "see in the Android Application (Which is the best Application ever by the way)";

        
        Set<PlayerInterface> players = new HashSet<>();
        for (UserInterface user : users) {
            players.add(new Player(user, getRandomRole()));
        }
        PlayerInterface admin = getRandomUser(players);
        ProjectInterface project = new Project(name, description, admin, players, backlog);

        for (UserInterface user : users) {
            user.getProjects().add(project);
        }

        return project;
    }
    
    private static UserInterface createUser(String name) {
        String username = name.toLowerCase(Locale.ENGLISH).replace(' ', '_');
        String email = name.toLowerCase(Locale.ENGLISH).replace(' ', '.') + "@gmail.com";
        UserInterface user = new User(name.hashCode(), name, username, email, new HashSet<ProjectInterface>());
        return user;
    }
    
    public static Role getRandomRole() {
        int index = RAND.nextInt(Role.values().length);
        Role[] roles = Role.values();
        return roles[index];
    }
    
    private static PlayerInterface getRandomUser(Set<PlayerInterface> players) {
        int index = RAND.nextInt(players.size());
        PlayerInterface[] playersArray =  players.toArray(new PlayerInterface[players.size()]);
        return playersArray[index];
    }

}
