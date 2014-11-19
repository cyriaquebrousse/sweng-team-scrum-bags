package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue.Builder;
import ch.epfl.scrumtool.network.Client;

/**
 * @author ketsio, zenhaeus
 * @author Cyriaque Brousse
 */

public final class Sprint implements Serializable, Comparable<Sprint> {
    private static final long serialVersionUID = -5819472452849232304L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.SPRINT";

    private final String key;
    private final String title;
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
    private Sprint(String id, String title, long deadline) {
        this.key = id;
        this.title = title;
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
    public String getKey() {
        return key;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Creates the sprint in the DS
     * 
     * @param project
     * @param callback
     */
    public void insert(final Project project, final Callback<Sprint> callback) {
        Client.getScrumClient().insertSprint(this, project, callback);
    }

    /**
     * Updates the sprint in the DS
     * 
     * @param ref
     * @param callback
     */
    public void update(final Sprint ref, final Callback<Boolean> callback) {
        Client.getScrumClient().updateSprint(this, ref, callback);
    }

    /**
     * Removes the sprint from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Boolean> callback) {
        Client.getScrumClient().deleteSprint(this, callback);
    }

    /**
     * Adds an issue to the sprint in the DS
     * 
     * @param issue
     * @param callback
     */
    public void addIssue(final Issue issue, final Callback<Boolean> callback) {
        Client.getScrumClient().addIssueToSprint(issue, this, callback);
    }

    /**
     * Removes the issue from the sprints in the DS
     * 
     * @param issue
     * @param callback
     */
    public void removeIssue(final Issue issue, final Callback<Boolean> callback) {
        Client.getScrumClient().removeIssueFromSprint(issue, this, callback);
    }

    /**
     * Load the issues of this sprint from the DS
     * 
     * @param callback
     */
    public void loadIssues(final Callback<List<Issue>> callback) {
        Client.getScrumClient().loadIssues(this, callback);
    }
    
    /**
     * Get new instance of Builder
     * @return
     */
    public Builder getBuilder() {
        return new Builder();
    }


    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     */

    public static class Builder {

        private String keyb;
        private String title;
        private long deadline;

        public Builder() {
            this.keyb = "";
            this.title = "";
            this.deadline = new Date().getTime();
        }

        /**
         * @param otherSprint
         */
        public Builder(Sprint otherSprint) {
            this.deadline = otherSprint.deadline;
            this.title = otherSprint.title;
            this.keyb = otherSprint.key;
        }

        /**
         * @return the id
         */
        public String getKey() {
            return keyb;
        }

        /**
         * @param id
         *            the id to set
         */
        public Sprint.Builder setKey(String id) {
            if (id != null) {
                this.keyb = id;
            }
            return this;
        }

        /**
         * @return the deadLine
         */
        public long getDeadline() {
            return deadline;
        }

        /**
         * @param deadline
         *            the deadline to set, in milliseconds
         * @see Date#getTime()
         */
        public Sprint.Builder setDeadline(long deadline) {
            this.deadline = deadline;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Sprint.Builder setTitle(String newTitle) {
            if (newTitle != null) {
                this.title = newTitle;
            }
            return this;
        }

        /**
         * @return
         */
        public Sprint build() {
            return new Sprint(this.keyb, this.title, this.deadline);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) o;
        return other.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
    
    @Override
    public int compareTo(Sprint that) {
        final int before = -1;
        final int equal = 0;
        final int after = 1;
        
        if(that != null) {
            if (this == that) {
                return equal;
            }
            
            if (this.getDeadline() < that.getDeadline()) {
                return before;
            }
            
            if (this.getDeadline() > that.getDeadline()) {
                return after;
            }
            
            int comparison = this.getTitle().compareTo(that.getTitle());
            
            return comparison;
        } else {
            return after;
        }
    }
}
