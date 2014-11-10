package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.network.Client;

/**
 * @author ketsio, zenhaeus
 * @author Cyriaque Brousse
 */

public final class Sprint {
    private final String id;
    private final long deadline;

    /**
     * Constructs a new sprint
     * 
     * @param id
     *            the unique identifier
     * @param deadline
     *            the deadline in milliseconds. Must be non-negative
     * @see java.util.Date#getTime()
     */
    private Sprint(String id, long deadline) {
        if (id == null || !deadlineIsValid(deadline)) {
            throw new IllegalArgumentException("Deadline was invalid or no id was provided");
        }
        this.id = id;
        this.deadline = deadline;
    }

    /**
     * @return the deadline in milliseconds
     * @see java.util.Date#getTime()
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * The set of issues is returned in a callback
     */
    public void loadIssues(DatabaseHandler<Sprint> db, Callback<List<Issue>> callback) {
        Client.getScrumClient().loadIssues(this, callback);
    }
    
    /**
     * Add existing Issue to this Sprint
     * @param db
     * @param callback
     */
    public void addIssue(Issue issue, DatabaseHandler<Sprint> db, Callback<Boolean> callback) {
        Client.getScrumClient().addIssue(issue, this, callback);
    }

    /**
     * Removes existing Issue to this Sprint
     * @param db
     * @param callback
     */
    public void removeIssue(Issue issue, DatabaseHandler<Sprint> db, Callback<Boolean> callback) {
        Client.getScrumClient().removeIssue(issue, this, callback);
    }

    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     */

    public static class Builder {
        
        private String id;
        private long deadline;
        private Set<Issue> issues;

        public Builder() {
            this.issues = new HashSet<Issue>();
        }

        public Builder(Sprint otherSprint) {
            this.deadline = otherSprint.deadline;
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
            return new HashSet<>(this.issues);
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
        public long getDeadline() {
            return deadline;
        }

        /**
         * @param deadline
         *            the deadline to set
         */
        public void setDeadline(long deadline) {
            if (deadlineIsValid(deadline)) {
                this.deadline = deadline;
            }
        }

        public Sprint build() {
            return new Sprint(this.id, this.deadline);
        }
    }

    /**
     * @param deadline
     *            the deadline whose validity is to be checked
     * @return true if the deadline is valid, false otherwise
     */
    private static boolean deadlineIsValid(long deadline) {
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
