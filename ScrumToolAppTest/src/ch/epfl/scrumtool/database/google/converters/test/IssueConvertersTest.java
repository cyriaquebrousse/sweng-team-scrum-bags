package ch.epfl.scrumtool.database.google.converters.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.TaskIssueProject;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.exception.InconsistentDataException;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * 
 * @author aschneuw
 *
 */
public class IssueConvertersTest extends TestCase {
    
    public void testNullKey() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setKey(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testNullDescription() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setDescription(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testNullName() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setName(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testNullEstimation() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setName(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testNullPriority() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setPriority(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testNullStatus() {
        try {
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            issue.setStatus(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testValidIssue() {
        ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
        ScrumPlayer player = ServerClientEntities.generateBasicScrumPlayer();
        ScrumSprint sprint = ServerClientEntities.generateBasicScrumSprint();
        issue.setAssignedPlayer(player);
        issue.setSprint(sprint);
        Issue converted = IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
        
        Issue testIssue = ServerClientEntities.generateBasicIssue();
        Player testPlayer = ServerClientEntities.generateBasicPlayer();
        Sprint testSprint = ServerClientEntities.generateBasicSprint();
        testIssue = testIssue.getBuilder()
            .setPlayer(testPlayer)
            .setSprint(testSprint)
            .build();

        assertEquals(converted, testIssue);
    }

    public void testIssueToScrumIssueEmptyKey() {
        Issue issue = ServerClientEntities.generateBasicIssue();
        issue = issue.getBuilder().setKey("").build();
        ScrumIssue ref = ServerClientEntities.generateBasicScrumIssue();
        ref.setKey(null);
        ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(issue);
        assertEquals(scrumIssue.getDescription(), ref.getDescription());
        assertEquals(scrumIssue.getEstimation(), ref.getEstimation());
        assertEquals(scrumIssue.getKey(), ref.getKey());
        assertEquals(scrumIssue.getName(), ref.getName());
        assertEquals(scrumIssue.getAssignedPlayer(), ref.getAssignedPlayer());
        assertEquals(scrumIssue.getPriority(), ref.getPriority());
        assertEquals(scrumIssue.getSprint(), ref.getSprint());
        assertEquals(scrumIssue.getStatus(), ref.getStatus());
    }
    
    public void testIssueToScrumIssueWithSprintAndPlayer() {
        Issue issue = ServerClientEntities.generateBasicIssue();
        issue = issue.getBuilder()
                .setPlayer(ServerClientEntities.generateBasicPlayer())
                .setSprint(ServerClientEntities.generateBasicSprint())
                .build();
        ScrumIssue ref = ServerClientEntities.generateBasicScrumIssue();
        ref.setAssignedPlayer(ServerClientEntities.generateBasicScrumPlayer());
        ref.setSprint(ServerClientEntities.generateBasicScrumSprint());
        ScrumIssue scrumIssue = IssueConverters.ISSUE_TO_SCRUMISSUE.convert(issue);
        assertEquals(scrumIssue.getDescription(), ref.getDescription());
        assertEquals(scrumIssue.getEstimation(), ref.getEstimation());
        assertEquals(scrumIssue.getKey(), ref.getKey());
        assertEquals(scrumIssue.getName(), ref.getName());
        assertEquals(scrumIssue.getAssignedPlayer().getKey(), ref.getAssignedPlayer().getKey());
        assertEquals(scrumIssue.getPriority(), ref.getPriority());
        assertEquals(scrumIssue.getSprint().getKey(), ref.getSprint().getKey());
        assertEquals(scrumIssue.getStatus(), ref.getStatus());
    }
    
    public void testInsertResponseToIssue() {
        KeyResponse response = new KeyResponse();
        response.setKey(ServerClientEntities.VALIDKEY);
        
        Issue issue = ServerClientEntities.generateBasicIssue();
        issue = issue.getBuilder()
                .setKey("")
                .build();
        
        InsertResponse<Issue> insresp = new InsertResponse<Issue>(issue, response);

        Issue keyIssue = IssueConverters.INSERTRESPONSE_TO_ISSUE.convert(insresp);
        assertEquals(ServerClientEntities.generateBasicIssue(), keyIssue);
    }
    
    public void testDashboardIssuesNullResponse() {
        CollectionResponseScrumIssue response = null;
        List<TaskIssueProject> conv = IssueConverters.DASHBOARD_ISSUES.convert(response);
        assertEquals(conv.size(), 0);
    }
    
    public void testDashboardIssuesNullItems() {
        CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
        List<TaskIssueProject> conv = IssueConverters.DASHBOARD_ISSUES.convert(response);
        assertEquals(conv.size(), 0);
    }
    
    public void testDashboardIssuesNullProject() {
        try {
            CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            issue.setMainTask(mainTask);
            List<ScrumIssue> list = new ArrayList<ScrumIssue>();
            list.add(issue);
            response.setItems(list);
            IssueConverters.DASHBOARD_ISSUES.convert(response);
            fail("InconsistentDataException expected");
            
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testDashboardIssuesNullTask() {
        try {
            
            CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
            ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
            List<ScrumIssue> list = new ArrayList<ScrumIssue>();
            list.add(issue);
            response.setItems(list);
            IssueConverters.DASHBOARD_ISSUES.convert(response);
            fail("InconsistentDataException expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testValidDashboardIssues() {
        CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
        ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
        ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
        ScrumProject project = ServerClientEntities.generateBasicScrumProject();
        mainTask.setProject(project);
        issue.setMainTask(mainTask);
        List<ScrumIssue> list = new ArrayList<ScrumIssue>();
        list.add(issue);
        response.setItems(list);
        List<TaskIssueProject> result = IssueConverters.DASHBOARD_ISSUES.convert(response);
        assertEquals(1, result.size());
        assertEquals(ServerClientEntities.generateBasicIssue(), result.get(0).getIssue());
        assertEquals(ServerClientEntities.generateBasicMainTask(), result.get(0).getMainTask());
        assertEquals(ServerClientEntities.generateBasicProject(), result.get(0).getProject());
    }
    
    public void testInsertResponseToMainTask() {
        KeyResponse response = new KeyResponse();
        response.setKey(ServerClientEntities.VALIDKEY);
        
        MainTask mainTask = ServerClientEntities.generateBasicMainTask();
        mainTask = mainTask.getBuilder()
                .setKey("")
                .build();
        
        InsertResponse<MainTask> insresp = new InsertResponse<MainTask>(mainTask, response);

        MainTask keyMainTask = MainTaskConverters.INSERTRESP_TO_MAINTASK.convert(insresp);
        assertEquals(ServerClientEntities.generateBasicMainTask(), keyMainTask);
    }
}

