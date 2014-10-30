package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio, zenhaeus
 */
public final class Sprint {
    private final Date deadLine;
    private final Set<Issue> issues;

    /**
     * @param deadLine
     * @param issues
     */
    private Sprint(Date deadLine, Set<Issue> issues) {
        super();
        if (deadLine == null || issues == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.deadLine = new Date(deadLine.getTime());
        this.issues = new HashSet<Issue>(issues);
    }

    /**
     * @return the mDeadLine
     */
    public Date getDeadLine() {
        return new Date(deadLine.getTime());
    }


    /**
     * @return the mIssues
     */
    public Set<Issue> getIssues() {
        return issues;
    }

    
    public static class Builder {
        private Date deadLine;
        private Set<Issue> issues;
        
        public Builder() {
            this.issues = new HashSet<Issue>();
        }
        
        public Builder(Sprint otherSprint) {
            this.deadLine = otherSprint.deadLine;
            this.issues = new HashSet<Issue>(otherSprint.issues);
        }

        /**
         * @return the mIssues
         */
        public Set<Issue> getIssues() {
            return issues;
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
        
        public Sprint build() {
            return new Sprint(this.deadLine, this.issues);
        }
    }
}
