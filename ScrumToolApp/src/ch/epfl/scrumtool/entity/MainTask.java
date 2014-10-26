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

    private final Set<Issue> mIssues;
    private Priority mPriority;

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
        this.mIssues = new HashSet<Issue>();
        for (Issue i : issues) {
            mIssues.add(new Issue(i));
        }
        this.mPriority = priority;

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
        for (Issue i : mIssues) {
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
            this.mIssues.add(new Issue(issue));
        }
    }

    /**
     * @param issue
     *            the issue to remove
     */
    public void removeIssue(Issue issue) {
        if (issue != null) {
            this.mIssues.remove(issue);
        }
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return mPriority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Priority priority) {
        if (priority != null) {
            this.mPriority = priority;
        }
    }

    public int getIssuesFinishedCount() {
        int count = 0;
        for (Issue i : mIssues) {
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
        for (Issue i : mIssues) {
            issueEstimation = i.getEstimatedTime();
            if (issueEstimation < 0) {
                estimated = false;
            } else {
                estimation += issueEstimation;
            }
        }
        return estimated ? estimation : -1;
    }
}
