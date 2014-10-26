package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio
 */
public final class Sprint {
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
        this.mIssues = new HashSet<Issue>();
        for (Issue i : issues) {
            mIssues.add(new Issue(i));
        }
    }

    /**
     * @param aSprint
     */
    public Sprint(Sprint aSprint) {
        this(aSprint.getDeadLine(), aSprint.getIssues());
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
            this.mIssues.add(issue);
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
}
