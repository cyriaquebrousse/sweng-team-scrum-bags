package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio
 */
public class Sprint {
    private Date mDeadLine;
    private final Set<Issue> mIssues;

    /**
     * @param mDeadLine
     * @param mIssues
     */
    public Sprint(Date deadLine, Set<Issue> issues) {
        super();
        if (deadLine == null || issues == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.mDeadLine = new Date(deadLine.getTime());
        this.mIssues = new HashSet<Issue>(issues);
    }

    /**
     * @return the mDeadLine
     */
    public Date getDeadLine() {
        return new Date(mDeadLine.getTime());
    }

    /**
     * @param mDeadLine
     *            the mDeadLine to set
     */
    public void setDeadLine(Date deadLine) {
        if (deadLine != null) {
            this.mDeadLine = new Date(deadLine.getTime());
        }
    }

    /**
     * @return the mIssues
     */
    public Set<Issue> getIssues() {
        return mIssues;
    }

    /**
     * @param issue
     *            the issue to add
     */
    public void addIssue(Issue issue) {
        if (issue != null) {
            this.mIssues.add(issue);
        }
    }

    /**
     * @param issue
     *            the issue to remove
     */
    public void removeIssue(Issue issue) {
        if (issue != null) {
            // TODO tester si mIssues contient issue?
            this.mIssues.remove(issue);
        }
    }
}
