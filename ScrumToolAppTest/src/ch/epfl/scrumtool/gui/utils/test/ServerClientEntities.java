package ch.epfl.scrumtool.gui.utils.test;

import java.util.ArrayList;
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
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
/**
 * Models Immutable client entities with the corresponding mutable service server objects
 * @author aschneuw
 *
 */
public final class ServerClientEntities {
    //Keys
    public static final String VALIDKEY = "key";
    public static final String INVALIDKEY = "";
    
    //Issue
    private static final String ISSUE_STRING = "issue";
    private static final float ISSUE_ESTIMATION = 10F;
    private static final Priority ISSUE_PRIO = Priority.HIGH;
    private static final ch.epfl.scrumtool.entity.Status ISSUE_STATUS = Status.READY_FOR_SPRINT;
    
    //Sprint
    private static final String SPRINT_STRING = "sprint";
    private static final long SPRINT_DATE = getDay(2014, 12, 07);
    
    //User
    private static final String TEST_USER_MAIL = "example@example.com";
    
    //Player
    private static final boolean PLAYER_ADMIN = false;
    private static final boolean PLAYER_INVITED = false;
    
    //MainTask
    private static final String MAINTASK_STRING = "maintask";
    private static final Priority MAINTASK_PRIO = Priority.NORMAL;
    private static final Status MAINTASK_STAT = Status.FINISHED;
    private static final int TOTAL_ISSUES = 3;
    private static final int FINISHED_ISSUES = 3;
    private static final float TOTAL_TIME = 300f;
    private static final float FINISHED_TIME = 200f;
    
    //Project
    private static final String PROJECT_STRING = "project";


    public static final Project TEST_PROJECT_W_KEY = new Project.Builder()
        .setDescription("Test Project")
        .setKey("key")
        .setName("Test Project")
        .build();

    public static final Project TEST_PROJECT_WO_KEY = new Project.Builder(TEST_PROJECT_W_KEY)
        .setKey("")
        .build();

    public static final KeyResponse VALID_KEY_RESPONSE = new KeyResponse().setKey(VALIDKEY);
    
    public static final KeyResponse INVALID_KEY_RESPONSE = new KeyResponse().setKey(INVALIDKEY);

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
        player.setKey(VALIDKEY);
        player.setUser(generateBasicScrumUser());
        player.setAdminFlag(PLAYER_ADMIN);
        player.setInvitedFlag(PLAYER_INVITED);
        player.setRole(Role.DEVELOPER.name());
        return player;
    }
    
    public static Player generateBasicPlayer() {
        Player.Builder builder = new Player.Builder();
        return builder.setKey(VALIDKEY)
            .setIsAdmin(PLAYER_ADMIN)
            .setIsInvited(PLAYER_INVITED)
            .setRole(Role.DEVELOPER)
            .setUser(generateBasicUser())
            .build();
    }
    
    public static ScrumIssue generateBasicScrumIssue() {
        ScrumIssue issue = new ScrumIssue();
        issue.setDescription(ISSUE_STRING);
        issue.setEstimation(ISSUE_ESTIMATION);
        issue.setKey(VALIDKEY);
        issue.setName(ISSUE_STRING);
        issue.setPriority(ISSUE_PRIO.name());
        issue.setStatus(ISSUE_STATUS.name());
        return issue;
    }
    
    public static Issue generateBasicIssue() {
        Issue.Builder builder = new Issue.Builder();
        return builder.setDescription(ISSUE_STRING)
            .setEstimatedTime(ISSUE_ESTIMATION)
            .setKey(VALIDKEY)
            .setName(ISSUE_STRING)
            .setPriority(ISSUE_PRIO)
            .setStatus(ISSUE_STATUS)
            .build();
    }
    
    public static ScrumSprint generateBasicScrumSprint() {
        ScrumSprint sprint = new ScrumSprint();
        sprint.setKey(VALIDKEY);
        sprint.setTitle(SPRINT_STRING);
        sprint.setDate(SPRINT_DATE);
        return sprint;
    }
    
    public static Sprint generateBasicSprint() {
        Sprint.Builder builder = new Sprint.Builder();
        return builder.setDeadline(SPRINT_DATE)
            .setKey(VALIDKEY)
            .setTitle(SPRINT_STRING)
            .build();
    }
    
    public static ScrumMainTask generateBasicScrumMainTask() {
        ScrumMainTask task = new ScrumMainTask();
        task.setDescription(MAINTASK_STRING);
        task.setKey(VALIDKEY);
        task.setName(MAINTASK_STRING);
        task.setPriority(MAINTASK_PRIO.name());
        task.setStatus(MAINTASK_STAT.name());
        task.setIssuesFinished(FINISHED_ISSUES);
        task.setTotalIssues(TOTAL_ISSUES);
        task.setTimeFinished(FINISHED_TIME);
        task.setTotalTime(TOTAL_TIME);
        return task;
    }
    
    public static MainTask generateBasicMainTask() {
        MainTask.Builder builder = new MainTask.Builder();
        return builder.setDescription(MAINTASK_STRING)
            .setFinishedIssues(FINISHED_ISSUES)
            .setFinishedIssueTime(FINISHED_TIME)
            .setKey(VALIDKEY)
            .setPriority(MAINTASK_PRIO)
            .setStatus(MAINTASK_STAT)
            .setTotalIssues(TOTAL_ISSUES)
            .setTotalIssueTime(TOTAL_TIME)
            .setName(MAINTASK_STRING)
            .build();
    }
    
    public static ScrumProject generateBasicScrumProject() {
        ScrumProject project = new ScrumProject();
        project.setDescription(PROJECT_STRING);
        project.setKey(VALIDKEY);
        project.setName(PROJECT_STRING);
        return project;
    }
    
    public static Project generateBasicProject() {
        Project.Builder builder = new Project.Builder();
        return builder.setDescription(PROJECT_STRING)
            .setKey(VALIDKEY)
            .setName(PROJECT_STRING)
            .build();
    }
    
    public static long getDay(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        return date.getTimeInMillis();
    }
    
    public static KeyResponse generateKeyResponse() {
        KeyResponse response = new KeyResponse();
        response.setKey(VALIDKEY);
        return response;
    }
    
    public static CollectionResponseScrumProject generateCollectionScrumProject() {
        CollectionResponseScrumProject resp = new CollectionResponseScrumProject();
        ScrumProject s1 = generateBasicScrumProject();
        ScrumProject s2 = generateBasicScrumProject();
        ArrayList<ScrumProject> list = new ArrayList<ScrumProject>();
        list.add(s1);
        list.add(s2);
        resp.setItems(list);
        return resp;
    }
    
    public static CollectionResponseScrumSprint generateCollectionScrumSprint() {
        CollectionResponseScrumSprint resp = new CollectionResponseScrumSprint();
        ScrumSprint s1 = generateBasicScrumSprint();
        ScrumSprint s2 = generateBasicScrumSprint();
        ArrayList<ScrumSprint> list = new ArrayList<ScrumSprint>();
        list.add(s1);
        list.add(s2);
        resp.setItems(list);
        return resp;
    }
    
    public static CollectionResponseScrumPlayer generateCollectionScrumPlayer() {
        CollectionResponseScrumPlayer resp = new CollectionResponseScrumPlayer();
        ScrumPlayer s1 = generateBasicScrumPlayer();
        ScrumPlayer s2 = generateBasicScrumPlayer();
        ArrayList<ScrumPlayer> list = new ArrayList<ScrumPlayer>();
        list.add(s1);
        list.add(s2);
        resp.setItems(list);
        return resp;
    }
    
    public static CollectionResponseScrumIssue generateCollectionScrumIssue() {
        CollectionResponseScrumIssue resp = new CollectionResponseScrumIssue();
        ScrumIssue s1 = generateBasicScrumIssue();
        ScrumIssue s2 = generateBasicScrumIssue();
        ArrayList<ScrumIssue> list = new ArrayList<ScrumIssue>();
        list.add(s1);
        list.add(s2);
        resp.setItems(list);
        return resp;
    }
    
    public static CollectionResponseScrumMainTask generateCollectionScrumMainTask() {
        CollectionResponseScrumMainTask resp = new CollectionResponseScrumMainTask();
        ScrumMainTask s1 = generateBasicScrumMainTask();
        ScrumMainTask s2 = generateBasicScrumMainTask();
        ArrayList<ScrumMainTask> list = new ArrayList<ScrumMainTask>();
        list.add(s1);
        list.add(s2);
        resp.setItems(list);
        return resp;
    }
}
