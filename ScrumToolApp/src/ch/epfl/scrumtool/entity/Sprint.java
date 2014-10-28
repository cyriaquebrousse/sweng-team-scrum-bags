package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio
 */
public final class Sprint {
    private Date deadLine;
    private final Set<Issue> issues;

    /**
     * @param deadLine
     * @param issues
     */
    public Sprint(Date deadLine, Set<Issue> issues) {
        super();
        if (deadLine == null || issues == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.deadLine = new Date(deadLine.getTime());
        this.issues = new HashSet<Issue>();
        for (Issue i : issues) {
            this.issues.add(new Issue(i));
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
        return new Date(deadLine.getTime());
    }

    /**
     * @param deadLine
     *            the mDeadLine to set
     */
    public void setDeadLine(Date deadLine) {
        if (deadLine != null) {
            this.deadLine = new Date(deadLine.getTime());
        }
    }

    /**
     * @return the mIssues
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
            this.issues.add(issue);
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) o;
        if (other.getDeadLine().getTime() != this.getDeadLine().getTime()) {
            return false;
        }
        if (!other.getIssues().equals(this.getIssues())) {
            return false;
        }
        return true;
    }

}
