package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;

/**
 * @author ketsio, zenhaeus
 */
public final class Sprint implements DatabaseInteraction<Sprint> {
    private final String id;
    private final long deadLine;
    private final Set<Issue> issues;

    /**
     * @param deadLine
     * @param issues
     */
    private Sprint(String id, Date deadLine, Set<Issue> issues) {
        super();
        if (id == null || issues == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.id = id;
        this.deadLine = deadLine.getTime();
        this.issues = new HashSet<Issue>(issues);
    }

    /**
     * @return the deadline in milliseconds
     * @see Date#getTime()
     */
    public long getDeadline() {
        return deadLine;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the set of issues
     */
    public Set<Issue> getIssues() {
        return issues;
    }

    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     * 
     */

    public static class Builder {
        private String id;
        private long deadLine;
        private Set<Issue> issues;

        public Builder() {
            this.issues = new HashSet<Issue>();
        }

        public Builder(Sprint otherSprint) {
            this.deadLine = otherSprint.deadLine;
            this.issues = new HashSet<Issue>(otherSprint.issues);

        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         *            the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the issues
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
         * @return the deadLine
         */
        public long getDeadLine() {
            return deadLine;
        }

        /**
         * @param deadLine
         *            the deadLine to set
         */
        public void setDeadLine(Date deadLine) {
            if (deadLine != null) {
                this.deadLine = deadLine.getTime();
            }
        }

        public Sprint build() {
            return new Sprint(this.id, new Date(this.deadLine), this.issues);
        }
    }

    @Override
    public void updateDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.update(this);
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.remove(this);
    }

    /**
     * @param deadline
     *            the deadline whose validity is to be checked
     * @return true if the deadline is valid, false otherwise
     */
    private boolean deadlineIsValid(long deadline) {
        return deadline >= 0;
    }

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
        if (other.getDeadline() != this.getDeadline()) {
            return false;
        }
        if (!other.getIssues().equals(this.getIssues())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
