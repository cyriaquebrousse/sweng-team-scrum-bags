/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent
 * 
 */
public final class MainTask extends AbstractTask {

    private final Set<Issue> issues;
    private Priority priority;

    /**
     * @param name
     * @param description
     * @param status
     * @param subtasks
     * @param priority
     */
    public MainTask(long id, String name, String description, Status status,
            Set<Issue> issues, Priority priority) {
        super(id, name, description, status);
        if (issues == null || priority == null) {
            throw new NullPointerException("MainTask.Constructor");
        }
        this.issues = new HashSet<Issue>();
        for (Issue i : issues) {
            this.issues.add(new Issue(i));
        }
        this.priority = priority;

    }

    /**
     * @param task
     */
    public MainTask(MainTask task) {
        this(task.getId(), task.getName(), task.getDescription(), task
                .getStatus(), task.getIssues(), task.getPriority());
    }

    /**
     * @return the subtasks
     */
    public Set<Issue> getIssues() {
        HashSet<Issue> tmp = new HashSet<Issue>();
        for (Issue i : issues) {
            tmp.add(new Issue(i));
        }
        return tmp;
    }

    /**
     * @param issue
     *            the issue to add
     */
    public void addIssue(Issue issue) {
        if (issue != null) {
            this.issues.add(new Issue(issue));
        }
    }

    /**
     * @param issue
     *            the issue to remove
     */
    public void removeIssue(Issue issue) {
        if (issue != null) {
            this.issues.remove(issue);
        }
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Priority priority) {
        if (priority != null) {
            this.priority = priority;
        }
    }

    public int getIssuesFinishedCount() {
        int count = 0;
        for (Issue i : issues) {
            if (i.getStatus() == Status.FINISHED) {
                ++count;
            }
        }
        return count;
    }

    public float getEstimatedTime() {
        float estimation = 0f;
        float issueEstimation;
        boolean estimated = true;
        for (Issue i : issues) {
            issueEstimation = i.getEstimatedTime();
            if (issueEstimation < 0) {
                estimated = false;
            } else {
                estimation += issueEstimation;
            }
        }
        return estimated ? estimation : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof MainTask)) {
            return false;
        }
        MainTask other = (MainTask) o;
        if (other.getPriority() != this.getPriority()) {
            return false;
        }
        if (other.getIssues() != this.getIssues()) {
            return false;
        }
        return super.equals(o);
    }
    
    @Override
    public int hashCode() {
        return (int) (super.hashCode()
                + priority.hashCode()
                + issues.hashCode()) % Integer.MAX_VALUE;
    }
}
