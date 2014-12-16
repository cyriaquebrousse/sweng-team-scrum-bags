package ch.epfl.scrumtool.database.google.converters.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.epfl.scrumtool.database.google.conversion.CollectionResponseConverters;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.database.google.conversion.PlayerConverters;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
import junit.framework.TestCase;
/**
 * 
 * @author aschneuw
 *
 */
public class CollectionResponseConvertersTest extends TestCase {
    public void testNullIssue() {
        List<Issue> issues = CollectionResponseConverters.ISSUES.convert(null);
        assertEquals(0, issues.size());
    }
    
    public void testIssueNullItems() {
        CollectionResponseScrumIssue resp = new CollectionResponseScrumIssue();
        List<Issue> issues = CollectionResponseConverters.ISSUES.convert(resp);
        assertEquals(0, issues.size());
    }
    
    public void test1Issue() {
        CollectionResponseScrumIssue resp = new CollectionResponseScrumIssue();
        ScrumIssue test = new ScrumIssue();
        final float estimation = 10F;
        test.setDescription("test");
        test.setEstimation(estimation);
        test.setKey("issue");
        test.setName("test");
        test.setPriority(Priority.HIGH.name());
        test.setStatus(ch.epfl.scrumtool.entity.Status.READY_FOR_ESTIMATION.name());
        ArrayList<ScrumIssue> list = new ArrayList<ScrumIssue>();
        list.add(test);
        resp.setItems(list);
        List<Issue> response = CollectionResponseConverters.ISSUES.convert(resp);
        assertEquals(response.get(0), IssueConverters.SCRUMISSUE_TO_ISSUE.convert(test));
    }
    
    public void testNullMainTask() {
        List<MainTask> issues = CollectionResponseConverters.MAINTASKS.convert(null);
        assertEquals(0, issues.size());
    }
    
    public void testMainTaskNullItems() {
        CollectionResponseScrumMainTask resp = new CollectionResponseScrumMainTask();
        List<MainTask> issues = CollectionResponseConverters.MAINTASKS.convert(resp);
        assertEquals(0, issues.size());
        
    }
    
    public void test1MainTask() {
        CollectionResponseScrumMainTask resp = new CollectionResponseScrumMainTask();
        ScrumMainTask test = new ScrumMainTask();
        test.setDescription("Test");
        test.setKey("key");
        test.setName("test");
        test.setPriority(Priority.NORMAL.name());
        test.setStatus(ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT.name());
        ArrayList<ScrumMainTask> list = new ArrayList<ScrumMainTask>();
        list.add(test);
        resp.setItems(list);
        List<MainTask> response = CollectionResponseConverters.MAINTASKS.convert(resp);
        assertEquals(response.get(0), MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(test));
    }
    
    public void testNullProject() {
        List<Project> issues = CollectionResponseConverters.PROJECTS.convert(null);
        assertEquals(0, issues.size());
    }
    
    public void testProjectNullItems() {
        CollectionResponseScrumProject resp = new CollectionResponseScrumProject();
        List<Project> issues = CollectionResponseConverters.PROJECTS.convert(resp);
        assertEquals(0, issues.size());
    }
    
    public void test1Project() {
        CollectionResponseScrumProject resp = new CollectionResponseScrumProject();
        ScrumProject test = new ScrumProject();
        test.setKey("test");
        test.setName("test");
        test.setDescription("test");
        ArrayList<ScrumProject> list = new ArrayList<ScrumProject>();
        list.add(test);
        resp.setItems(list);
        List<Project> response = CollectionResponseConverters.PROJECTS.convert(resp);
        assertEquals(response.get(0), ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(test));
    }
    
    public void testNullP() {
        List<Sprint> sprints = CollectionResponseConverters.SPRINTS.convert(null);
        assertEquals(0, sprints.size());
    }
    
    public void testSprintNullItems() {
        CollectionResponseScrumSprint resp = new CollectionResponseScrumSprint();
        List<Sprint> sprints = CollectionResponseConverters.SPRINTS.convert(resp);
        assertEquals(0, sprints.size());
        
    }
    
    public void test1Sprint() {
        CollectionResponseScrumSprint resp = new CollectionResponseScrumSprint();
        ScrumSprint test = new ScrumSprint();
        test.setKey("test");
        test.setTitle("test");
        Calendar date = Calendar.getInstance();
        final int year = 1999;
        final int month = 1;
        final int day = 1;
        date.set(year, month, day);
        test.setDate(date.getTimeInMillis());
        ArrayList<ScrumSprint> list = new ArrayList<ScrumSprint>();
        list.add(test);
        resp.setItems(list);
        List<Sprint> response = CollectionResponseConverters.SPRINTS.convert(resp);
        assertEquals(response.get(0), SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(test));
    }
    
    public void testNullPlayer() {
        List<Player> players = CollectionResponseConverters.PLAYERS.convert(null);
        assertEquals(0, players.size());
    }
    
    public void testPlayerNullItems() {
        CollectionResponseScrumPlayer resp = new CollectionResponseScrumPlayer();
        List<Player> players = CollectionResponseConverters.PLAYERS.convert(resp);
        assertEquals(0, players.size());
    }
    
    public void test1Player() {
        CollectionResponseScrumPlayer resp = new CollectionResponseScrumPlayer();
        ScrumUser user = new ScrumUser();
        user.setEmail("test@test.com");
        ScrumPlayer test = new ScrumPlayer();
        test.setKey("test");
        test.setUser(user);
        test.setAdminFlag(false);
        test.setInvitedFlag(false);
        test.setRole(Role.DEVELOPER.name());

        ArrayList<ScrumPlayer> list = new ArrayList<ScrumPlayer>();
        list.add(test);
        resp.setItems(list);
        List<Player> response = CollectionResponseConverters.PLAYERS.convert(resp);
        assertEquals(response.get(0), PlayerConverters.SCRUMPLAYER_TO_PLAYER.convert(test));
    }
}
