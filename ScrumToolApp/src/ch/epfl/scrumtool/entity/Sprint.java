package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.util.Preconditions;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

/**
 * Represents a sprint
 * 
 * @author ketsio
 * @author zenhaeus
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
     * @param key
     *            the unique identifier
     * @param deadline
     *            the deadline in milliseconds. Must be non-negative
     * @see java.util.Date#getTime()
     */
    private Sprint(String key, String title, long deadline) {
        throwIfNull("Sprint constructor parameters cannot be null", key, title);
        Preconditions.throwIfEmptyString("Sprint title must not be empty", title);
        this.key = key;
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
    public void update(final Callback<Void> callback) {
        Client.getScrumClient().updateSprint(this, callback);
    }

    /**
     * Removes the sprint from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Void> callback) {
        Client.getScrumClient().deleteSprint(this, callback);
    }

    /**
     * Adds an issue to the sprint in the DS
     * 
     * @param issue
     * @param callback
     */
    public void addIssue(final Issue issue, final Callback<Void> callback) {
        issue.addToSprint(this, callback);
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
        return new Builder(this);
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
            this.deadline = Calendar.getInstance().getTimeInMillis();
        }

        /**
         * Copy constructor
         * 
         * @param otherSprint
         *            the sprint to copy
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

        /**
         * 
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * 
         * @param newTitle
         * @return the current builder instance
         */
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
        return other.key.equals(this.key)
                && other.deadline == this.deadline
                && other.title == this.title;
    }

    /**
     * based on it's key
     */
    @Override
    public int hashCode() {
        return key.hashCode();
    }
    
    /**
     * Order: Deadline->Title
     */
    @Override
    public int compareTo(Sprint that) {
        if (that == null) {
            return 1;
        }

        if (this.getDeadline() < that.getDeadline()) {
            return -1;
        }

        if (this.getDeadline() > that.getDeadline()) {
            return 1;
        }

        return this.getTitle().compareTo(that.getTitle());
    }
}
