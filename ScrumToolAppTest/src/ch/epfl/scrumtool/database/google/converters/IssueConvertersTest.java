package ch.epfl.scrumtool.database.google.converters;

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
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.utils.TestConstants;

public class IssueConvertersTest extends TestCase {
    
    public void testNullKey() {
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setKey(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullDescription() {
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setDescription(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullName(){
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setName(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullEstimation() {
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setName(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullPriority() {
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setPriority(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullStatus() {
        try {
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            issue.setStatus(null);
            IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testValidIssue() {
        ScrumIssue issue = TestConstants.generateBasicScrumIssue();
        ScrumPlayer player = TestConstants.generateBasicScrumPlayer();
        ScrumSprint sprint = TestConstants.generateBasicScrumSprint();
        issue.setAssignedPlayer(player);
        issue.setSprint(sprint);
        Issue converted = IssueConverters.SCRUMISSUE_TO_ISSUE.convert(issue);
        
        Issue testIssue = TestConstants.generateBasicIssue();
        Player testPlayer = TestConstants.generateBasicPlayer();
        Sprint testSprint = TestConstants.generateBasicSprint();
        testIssue = testIssue.getBuilder()
            .setPlayer(testPlayer)
            .setSprint(testSprint)
            .build();

        assertEquals(converted, testIssue);
        }

    public void testIssueToScrumIssueEmptyKey() {
        Issue issue = TestConstants.generateBasicIssue();
        issue = issue.getBuilder().setKey("").build();
        ScrumIssue ref = TestConstants.generateBasicScrumIssue();
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
        Issue issue = TestConstants.generateBasicIssue();
        issue = issue.getBuilder()
                .setPlayer(TestConstants.generateBasicPlayer())
                .setSprint(TestConstants.generateBasicSprint())
                .build();
        ScrumIssue ref = TestConstants.generateBasicScrumIssue();
        ref.setAssignedPlayer(TestConstants.generateBasicScrumPlayer());
        ref.setSprint(TestConstants.generateBasicScrumSprint());
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
        response.setKey(TestConstants.validKey);
        
        Issue issue = TestConstants.generateBasicIssue();
        issue = issue.getBuilder()
                .setKey("")
                .build();
        
        InsertResponse<Issue> insresp = new InsertResponse<Issue>(issue, response);

        Issue keyIssue = IssueConverters.INSERTRESPONSE_TO_ISSUE.convert(insresp);
        assertEquals(TestConstants.generateBasicIssue(), keyIssue);
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
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            issue.setMainTask(mainTask);
            List<ScrumIssue> list = new ArrayList<ScrumIssue>();
            list.add(issue);
            response.setItems(list);
            IssueConverters.DASHBOARD_ISSUES.convert(response);
            fail("NullPointerException expected");
            
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testDashboardIssuesNullTask() {
        try {
            
            CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
            ScrumIssue issue = TestConstants.generateBasicScrumIssue();
            List<ScrumIssue> list = new ArrayList<ScrumIssue>();
            list.add(issue);
            response.setItems(list);
            IssueConverters.DASHBOARD_ISSUES.convert(response);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected2");
        }
    }
    
    public void testValidDashboardIssues() {
        CollectionResponseScrumIssue response = new CollectionResponseScrumIssue();
        ScrumIssue issue = TestConstants.generateBasicScrumIssue();
        ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
        ScrumProject project = TestConstants.generateBasicScrumProject();
        mainTask.setProject(project);
        issue.setMainTask(mainTask);
        List<ScrumIssue> list = new ArrayList<ScrumIssue>();
        list.add(issue);
        response.setItems(list);
        List<TaskIssueProject> result = IssueConverters.DASHBOARD_ISSUES.convert(response);
        assertEquals(1, result.size());
        assertEquals(TestConstants.generateBasicIssue(),result.get(0).getIssue());
        assertEquals(TestConstants.generateBasicMainTask(), result.get(0).getMainTask());
        assertEquals(TestConstants.generateBasicProject(), result.get(0).getProject());
    }
    
    public void testInsertResponseToMainTask() {
        KeyResponse response = new KeyResponse();
        response.setKey(TestConstants.validKey);
        
        MainTask mainTask = TestConstants.generateBasicMainTask();
        mainTask = mainTask.getBuilder()
                .setKey("")
                .build();
        
        InsertResponse<MainTask> insresp = new InsertResponse<MainTask>(mainTask, response);

        MainTask keyMainTask = MainTaskConverters.INSERTRESP_TO_MAINTASK.convert(insresp);
        assertEquals(TestConstants.generateBasicMainTask(), keyMainTask);
    }
}

