package ch.epfl.scrumtool.database.google.containers.test;

import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class TaskIssueProjectTest extends TestCase {
    private static Project project = ServerClientEntities.generateBasicProject();
    private static Issue issue = ServerClientEntities.generateBasicIssue();
    private static MainTask mainTask = ServerClientEntities.generateBasicMainTask();

    public void testNullProject() {
        try {
            new TaskIssueProject(mainTask, null, issue);
            fail("NullPointerException expected for missing Project");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testNullIssue() {
        try {
            new TaskIssueProject(mainTask, project, null);
            fail("NullPointerException expected for missing issue");
        } catch (NullPointerException n) {
            
        }
    }
    
    public void testNullTask() {
        try {
            new TaskIssueProject(null, project, issue);
            fail("NullPointerException expected for missing maintask");
        } catch (NullPointerException n) {
            
        }
    }

    public void testGetMainTask() {
        TaskIssueProject t = new TaskIssueProject(mainTask, project, issue);
        assertEquals(mainTask, t.getMainTask());
    }

    public void testGetProject() {
        TaskIssueProject t = new TaskIssueProject(mainTask, project, issue);
        assertEquals(issue, t.getIssue());
    }

    public void testGetIssue() {
        TaskIssueProject t = new TaskIssueProject(mainTask, project, issue);
        assertEquals(project, t.getProject());
    }

    public void testCompareTo() {
        Issue i1 = issue.getBuilder()
                .setPriority(Priority.LOW)
                .setStatus(Status.READY_FOR_ESTIMATION)
                .build();
        TaskIssueProject t1 = new TaskIssueProject(mainTask, project, i1);
        Issue i2 = issue.getBuilder()
                .setPriority(Priority.HIGH)
                .setStatus(Status.READY_FOR_ESTIMATION)
                .build();
        TaskIssueProject t2 = new TaskIssueProject(mainTask, project, i2);
        assertTrue(t1.compareTo(t2) > 0);
    }
}