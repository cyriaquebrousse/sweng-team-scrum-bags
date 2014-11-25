/**
 * 
 */
package ch.epfl.scrumtool.database;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;

/**
 * @author aschneuw
 *
 */
public final class TaskIssueProject {
    private final MainTask mainTask;
    private final Project project;
    private final Issue issue;
    
    /**
     * 
     */
    public TaskIssueProject(final MainTask mainTask, final Project project, final Issue issue) {
        this.mainTask = mainTask;
        this.project = project;
        this.issue = issue;
    }

    /**
     * @return the mainTask
     */
    public MainTask getMainTask() {
        return mainTask;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @return the issue
     */
    public Issue getIssue() {
        return issue;
    }
    
}
