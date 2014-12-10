package ch.epfl.scrumtool.gui.utils;

import java.util.Calendar;


import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;

public class MockData {
    
    public static long CURRENT_TIME = Calendar.getInstance().getTimeInMillis();
    
    // Users
    public static User VINCENT = buildUser("vincent.debieux@gmail.com", "Vincent", "Débieux", CURRENT_TIME, "EPFL", Gender.MALE, 
            "Student");
    public static User JOEY = buildUser("joeyzenh@gmail.com", "Joey", "Zenhausern", CURRENT_TIME, "EPFL", Gender.MALE, 
            "Student");
    
    // Players
    public static Player VINCENT_ADMIN = buildPlayer("player1", true, false, Role.PRODUCT_OWNER, VINCENT);
    public static Player JOEY_DEV = buildPlayer("player2", false, true, Role.DEVELOPER, JOEY);
    
    // Projects
    public static Project MURCS = buildProject("project1", "Murcs", "murcs description");
    
    // Sprints
    public static Sprint SPRINT1 = buildSprint("sprint1", "week 1", CURRENT_TIME);
    public static Sprint SPRINT2 = buildSprint("sprint2", "week 2", CURRENT_TIME);
    
    // Maintasks
    public static MainTask TASK1 = buildMaintask("task1", "write tests", "description", Priority.HIGH, Status.IN_SPRINT,
            1000, 200, 5, 2);
    
    // Issues
    public static Issue ISSUE1 = buildIssue("Issue1", "tests for server", "desc", 200, Priority.URGENT,
            Status.FINISHED, VINCENT_ADMIN, SPRINT1);
    public static Issue ISSUE2 = buildIssue("Issue2", "test", "test desc", 5, Priority.NORMAL,
            Status.IN_SPRINT, JOEY_DEV, SPRINT1);

    private static Sprint buildSprint(String key, String title, long deadline) {
        Sprint.Builder sprint = new Sprint.Builder();
        sprint.setKey(key)
            .setTitle(title)
            .setDeadline(deadline);
        return sprint.build();
    }
    
    private static MainTask buildMaintask(String key, String name, String description, Priority priority,
            Status status, float totalTime, float finishedTime, int totalIssues, int finishedIssues) {
        MainTask.Builder maintask = new MainTask.Builder();
        maintask.setKey(key)
            .setName(name)
            .setDescription(description)
            .setTotalIssueTime(totalTime)
            .setFinishedIssueTime(finishedTime)
            .setTotalIssues(totalIssues)
            .setTotalIssues(totalIssues)
            .setFinishedIssues(finishedIssues);
        return maintask.build();
    }
    
    private static Issue buildIssue(String key, String name, String description, float time, Priority priority,
            Status status, Player player, Sprint sprint) {
        Issue.Builder issue = new Issue.Builder();
        issue.setKey(key)
            .setName(name)
            .setDescription(description)
            .setEstimatedTime(time)
            .setPriority(priority)
            .setStatus(status)
            .setPlayer(player)
            .setSprint(sprint);
        return issue.build();
    }
    
    private static Project buildProject(String key, String name, String description) {
        Project.Builder project = new Project.Builder();
        project.setKey(key)
            .setName(name)
            .setDescription(description);
        return project.build();
    }

    private static User buildUser(String email, String name, String lastName, long birth, String company, Gender sex,
            String proffession) {
        User.Builder user = new User.Builder();
        user.setEmail(email)
            .setName(name)
            .setLastName(lastName)
            .setDateOfBirth(birth)
            .setCompanyName(company)
            .setGender(sex)
            .setJobTitle(proffession);
        return user.build();
        
    }
    
    private static Player buildPlayer(String key, boolean admin, boolean invited, Role role, User user) {
        Player.Builder player = new Player.Builder();
        player.setKey(key)
            .setIsAdmin(admin)
            .setIsInvited(invited)
            .setRole(role)
            .setUser(user);
        return player.build();
    }
}
