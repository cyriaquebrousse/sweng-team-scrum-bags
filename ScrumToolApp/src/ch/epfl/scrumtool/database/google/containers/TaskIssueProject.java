/**
 * 
 */
package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * 
 * This is a container to contain an issue with it's related project and MainTask
 * 
 * @author aschneuw
 *
 */
public final class TaskIssueProject implements Comparable<TaskIssueProject> {
    private final MainTask mainTask;
    private final Project project;
    private final Issue issue;
    
    /**
     * 
     */
    public TaskIssueProject(final MainTask mainTask, final Project project, final Issue issue) {
        Preconditions.throwIfNull("TaskIssueProject wrapper must have valid objects", mainTask, project, issue);
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

    /**
     * Compares the issues only
     */
    @Override
    public int compareTo(TaskIssueProject that) {
        return getIssue().compareTo(that.getIssue());
    }
    
}
