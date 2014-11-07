package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    /**
     * @param deadLine
     * @param issues
     */
    private Sprint(String id, Date deadLine) {
        super();
        if (id == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.id = id;
        this.deadLine = deadLine.getTime();
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
    public void loadIssues(DatabaseHandler<Sprint> db, Callback<List<Issue>> callback) {
        //TODO define function in DSSprintHandler: db.loadIssues(this.id, callback);
    }
    
    /**
     * Add existing Issue to this Sprint
     * @param db
     * @param callback
     */
    public void addIssue(String issueKey, DatabaseHandler<Sprint> db, Callback<Boolean> callback) {
        // Cast handler to DSSprintHandler since this is a Sprint specific method
        //TODO define function in DSSprintHandler: db.addIssue(issueKey, this.id, callback);
    }

    /**
     * Removes existing Issue to this Sprint
     * @param db
     * @param callback
     */
    public void removeIssue(String issueKey, DatabaseHandler<Sprint> db, Callback<Boolean> callback) {
        // Cast handler to DSSprintHandler since this is a Sprint specific method
        //TODO define function in DSSprintHandler: db.removeIssue(issueKey, this.id, callback);
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
            return new Sprint(this.id, new Date(this.deadLine));
        }
    }

    @Override
    public void updateDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        handler.update(this, successCb);
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        handler.remove(this, successCb);
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
        if (o == null || !(o instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) o;
        return other.id.equals(this.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
