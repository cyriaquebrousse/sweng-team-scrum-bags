package ch.epfl.scrumtool.utils;

import java.util.Calendar;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

public final class TestConstants {
    public static final String validKey = "key";
    public static final String invalidKey = "";
    private static final String issueString = "issue";
    private static final String taskString = "maintask";
    private static final float issueEstimation = 10F;
    private static final Priority issuePrio = Priority.HIGH;
    private static final ch.epfl.scrumtool.entity.Status issueStatus = Status.READY_FOR_SPRINT;
    private static final String SPRINT_STRING = "sprint";
    private static final long SPRINT_DATE = getDay(2014, 12, 07);
    private static final String TEST_USER_MAIL = "test@test.com";
    private static final boolean PLAYER_ADMIN = false;
    private static final boolean PLAYER_INVITED = false;


    public static final Project TEST_PROJECT_W_KEY = new Project.Builder()
    .setDescription("Test Project")
    .setKey("key")
    .setName("Test Project")
    .build();

    public static final Project TEST_PROJECT_WO_KEY = new Project.Builder(TEST_PROJECT_W_KEY)
    .setKey("")
    .build();

    public static final KeyResponse VALID_KEY_RESPONSE = new KeyResponse().setKey(validKey);
    
    public static final KeyResponse INVALID_KEY_RESPONSE = new KeyResponse().setKey(invalidKey);

    public static ScrumUser generateBasicScrumUser() {
        ScrumUser user = new ScrumUser();
        user.setEmail(TEST_USER_MAIL);
        return user;
    }
    
    public static User generateBasicUser() {
        User.Builder builder = new User.Builder();
        return builder.setEmail(TEST_USER_MAIL)
                .build();
    }
    
    public static ScrumPlayer generateBasicScrumPlayer() {
        ScrumPlayer player = new ScrumPlayer();
        player.setKey(validKey);
        player.setUser(generateBasicScrumUser());
        player.setAdminFlag(PLAYER_ADMIN);
        player.setInvitedFlag(PLAYER_INVITED);
        player.setRole(Role.DEVELOPER.name());
        return player;
    }
    
    public static Player generateBasicPlayer() {
        Player.Builder builder = new Player.Builder();
        return builder.setKey(validKey)
            .setIsAdmin(PLAYER_ADMIN)
            .setIsInvited(PLAYER_INVITED)
            .setRole(Role.DEVELOPER)
            .setUser(generateBasicUser())
            .build();
    }
    
    public static ScrumIssue generateBasicScrumIssue() {
        ScrumIssue issue = new ScrumIssue();
        issue.setDescription(issueString);
        issue.setEstimation(issueEstimation);
        issue.setKey(validKey);
        issue.setName(issueString);
        issue.setPriority(issuePrio.name());
        issue.setStatus(issueStatus.name());
        return issue;
    }
    
    public static Issue generateBasicIssue() {
        Issue.Builder builder = new Issue.Builder();
        return builder.setDescription(issueString)
            .setEstimatedTime(issueEstimation)
            .setKey(validKey)
            .setName(issueString)
            .setPriority(issuePrio)
            .setStatus(issueStatus)
            .build();
    }
    
    public static ScrumSprint generateBasicScrumSprint() {
        ScrumSprint sprint = new ScrumSprint();
        sprint.setKey(validKey);
        sprint.setTitle(SPRINT_STRING);
        sprint.setDate(SPRINT_DATE);
        return sprint;
    }
    
    public static Sprint generateBasicSprint() {
        Sprint.Builder builder = new Sprint.Builder();
        return builder.setDeadline(SPRINT_DATE)
            .setKey(validKey)
            .setTitle(SPRINT_STRING)
            .build();
    }
    
    public static long getDay(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        return date.getTimeInMillis();
    }
}
