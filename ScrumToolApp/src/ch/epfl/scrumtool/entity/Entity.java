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

    // Users
    public static final User JOHN_SMITH = createUser(0, "John Smith");
    public static final User MARIA_LINDA = createUser(1, "Maria Linda");
    public static final User CYRIAQUE_BROUSSE = createUser(2, "Cyriaque Brousse");
    public static final User LORIS_LEIVA = createUser(3, "Loris Leiva");
    public static final User ARJEN_LENSTRA = createUser(4, "Arjen Lenstra");
    public static final User MICHAEL_SCOFIELD = createUser(5, "Michael Scofield");
    
    // Connected user
    public static final User CONNECTED_USER = MARIA_LINDA;
    
    // Issues
    public static final Issue ISSUE_A1 = createIssue(1, "Create the profile", Status.FINISHED, 3);
    public static final Issue ISSUE_A2 = createIssue(2, "Create the project", Status.IN_SPRINT, 2);
    public static final Issue ISSUE_A3 = createIssue(3, "Create the task", Status.FINISHED, 0);
    public static final Issue ISSUE_B1 = createIssue(4, "Take an empty cup", Status.IN_SPRINT, 0.5f);
    public static final Issue ISSUE_B2 = createIssue(5, "Put the coffe on it", Status.IN_SPRINT, 0.3f);
    public static final Issue ISSUE_C1 = createIssue(6, "Call Google", Status.IN_SPRINT, 10);
    public static final Issue ISSUE_D1 = createIssue(7, "Why ?", Status.FINISHED, 100);
    public static final Issue ISSUE_D2 = createIssue(8, "Why not ?", Status.FINISHED, 0.5f);
    public static final Issue ISSUE_E1 = createIssue(9, "Find time to sleep", Status.READY_FOR_ESTIMATION, 2);


    
    // Tasks
    public static final MainTask TASK_A = createTask(0, "Create Mock UI", Status.IN_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_A1, ISSUE_A2, ISSUE_A3)));
    public static final MainTask TASK_B = createTask(1, "Take a Coffee", Status.READY_FOR_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_B1, ISSUE_B2)));
    public static final MainTask TASK_C = createTask(2, "Implement the Google App Engine server", Status.IN_SPRINT,
            new HashSet<>(Arrays.asList(ISSUE_C1)));
    public static final MainTask TASK_D = createTask(3, "Find the meaning of the univers", Status.FINISHED,
            new HashSet<>(Arrays.asList(ISSUE_D1, ISSUE_D2)));
    public static final MainTask TASK_E = createTask(4, "Having a good night sleep", Status.READY_FOR_ESTIMATION,
            new HashSet<>(Arrays.asList(ISSUE_E1)));
    
    
    // Projects
    public static final Project COOL_PROJECT = createProject(0, "Cool Project",
            new HashSet<>(Arrays.asList(JOHN_SMITH, MARIA_LINDA, CYRIAQUE_BROUSSE, LORIS_LEIVA)),
            new HashSet<>(Arrays.asList(TASK_A, TASK_B)));
    
    public static final Project SUPER_PROJECT = createProject(1, "Super Project",
            new HashSet<>(Arrays.asList(JOHN_SMITH, MARIA_LINDA, ARJEN_LENSTRA, MICHAEL_SCOFIELD)),
            new HashSet<>(Arrays.asList(TASK_C, TASK_D)));


    /**
     * @param string
     * @param readyForEstimation
     * @return
     */
    static Issue createIssue(long id, String name, Status status, float estimation) {
        String description = "This is the description of the issue called + \"" + name + "\"";
        Player player = new Player(0, JOHN_SMITH, getRandomRole());
        Issue issue = new Issue(id, name, description, status, estimation, player);
        return issue;
    }

    /**
     * @param string
     * @return
     */
    static MainTask createTask(long id, String name, Status status, Set<Issue> issues) {
        String description = "This is the description of the task called \"" + name + "\"";
        MainTask task = new MainTask(id, name, description, status, issues, Priority.NORMAL);
        return task;
    }


    static Project createProject(long id, String name, Set<User> users, Set<MainTask> backlog) {
        String description = "This is a description for the project called \"";
        description += name + "\". This project is one of the best you'll ever ";
        description += "see in the Android Application (Which is the best Application ever by the way)";

        
        Set<Player> players = new HashSet<Player>();
        for (User user : users) {
            players.add(new Player(0, user, getRandomRole()));
        }
        Player admin = getRandomPlayer(players);
        Project project = new Project(id, name, description, admin, players, backlog, new HashSet<Sprint>());

        for (User user : users) {
            user.getProjects().add(project);
        }

        return project;
    }
    
    static User createUser(long id, String name) {
        String username = name.toLowerCase(Locale.ENGLISH).replace(' ', '_');
        String email = name.toLowerCase(Locale.ENGLISH).replace(' ', '.') + "@gmail.com";
        User user = new User(id, email, name, username, id, new HashSet<Project>());
        return user;
    }
    
    public static Role getRandomRole() {
        int index = RAND.nextInt(Role.values().length);
        Role[] roles = Role.values();
        return roles[index];
    }
    
    private static Player getRandomPlayer(Set<Player> players) {
        int index = RAND.nextInt(players.size());
        Player[] playersArray =  players.toArray(new Player[players.size()]);
        return playersArray[index];
    }

}
