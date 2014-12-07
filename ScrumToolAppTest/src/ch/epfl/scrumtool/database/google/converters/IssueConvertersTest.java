package ch.epfl.scrumtool.database.google.converters;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.conversion.IssueConverters;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
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
    }

