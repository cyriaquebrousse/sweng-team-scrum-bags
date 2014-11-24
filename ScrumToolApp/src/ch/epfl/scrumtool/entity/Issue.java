package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;

/**
 * A class that represent an Issue (child of a Maintask)
 * 
 * @author Vincent, zenhaeus
 * 
 */
public final class Issue extends AbstractTask implements Serializable, Comparable<Issue> {

    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.ISSUE";
    private static final long serialVersionUID = -1590796103232831763L;

    private float estimatedTime;
    private Player player;
    private Sprint sprint;

    /**
     * @param key
     * @param name
     * @param description
     * @param status
     * @param priority
     * @param estimatedTime
     * @param player
     */
    private Issue(String key, String name, String description, Status status,
            Priority priority, float estimatedTime, Player player, Sprint sprint) {
        super(key, name, description, status, priority);
        this.estimatedTime = estimatedTime;
        this.player = player;
        this.sprint = sprint;

    }

    /**
     * @return the mEstimatedTime
     */
    public float getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * @return the mProgramer
     */
    public Player getPlayer() {
        return player;
    }
    
    public Sprint getSprint() {
        return sprint;
    }

    /**
     * Creates the issue in the DS
     * 
     * @param task
     * @param callback
     */
    public void insert(final MainTask task, final Callback<Issue> callback) {
        Client.getScrumClient().insertIssue(this, task, callback);
    }

    /**
     * Updates the issue in the DS
     * 
     * @param ref
     * @param mainTask
     * @param callback
     */
    public void update(final Issue ref, Callback<Boolean> callback) {
        Client.getScrumClient().updateIssue(this, ref, callback);
    }

    /**
     * Removes the issue from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Boolean> callback) {
        Client.getScrumClient().deleteIssue(this, callback);
    }

    /**
     * Add an issue to the sprint on the DS
     * 
     * @param sprint
     * @param callback
     */
    public void addToSprint(final Sprint sprint,
            final Callback<Boolean> callback) {
        Client.getScrumClient().addIssueToSprint(this, sprint, callback);
    }

    /**
     * Removes the issue from the sprint on the DS
     * 
     * @param sprint
     * @param callback
     */
    public void removeFromSprint(final Sprint sprint,
            final Callback<Boolean> callback) {
        Client.getScrumClient().removeIssueFromSprint(this, callback);
    }
    
    public float estimatedTime(final List<Issue> issueList) {
        float total = 0;
        for (Issue is: issueList) {
            total += is.getEstimatedTime();
        }
        return total;
    }
    
    /**
     * Get new instance of Builder
     * @return
     */
    public Builder getBuilder() {
        return new Builder();
    }

    /**
     * A Builder for the Issue
     * 
     * @author
     * 
     */
    public static class Builder {
        private String key;
        private String name;
        private String description;
        private Status status;
        private Priority priority;
        private float estimatedTime;
        private Player player;
        private Sprint sprint;

        public Builder() {
            this.key = "";
            this.name = "";
            this.description = "";
            this.estimatedTime = 0f;
            this.priority = Priority.NORMAL;
            this.status = Status.READY_FOR_ESTIMATION;
        }

        public Builder(Issue otherIssue) {
            this.key = otherIssue.getKey();
            this.name = otherIssue.getName();
            this.description = otherIssue.getDescription();
            this.status = otherIssue.getStatus();
            this.priority = otherIssue.getPriority();
            this.estimatedTime = otherIssue.estimatedTime;
            this.player = otherIssue.player;
            this.sprint = otherIssue.sprint;
        }

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param key
         */
        public Issue.Builder setKey(String key) {
            if (key != null) {
                this.key = key;
            }
            return this;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         */
        public Issue.Builder setName(String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description
         */
        public Issue.Builder setDescription(String description) {
            if (description != null) {
                this.description = description;
            }
            return this;
        }

        /**
         * @return the status
         */
        public Status getStatus() {
            return status;
        }

        /**
         * @param status
         */
        public Issue.Builder setStatus(Status status) {
            if (status != null) {
                this.status = status;
            }
            return this;
        }

        /**
         * @return the status
         */
        public Priority getPriority() {
            return this.priority;
        }

        /**
         * @param status
         */
        public Issue.Builder setPriority(Priority priority) {
            if (priority != null) {
                this.priority = priority;
            }
            return this;
        }

        /**
         * @return the estimated time
         */
        public float getEstimatedTime() {
            return estimatedTime;
        }

        /**
         * @param estimatedTime
         */
        public Issue.Builder setEstimatedTime(float estimatedTime) {
            this.estimatedTime = estimatedTime;
            return this;
        }

        /**
         * @return the player
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * @param player
         */
        public Issue.Builder setPlayer(Player player) {
            if (player != null) {
                this.player = player;
            }
            return this;
        }
        
        /**
         * 
         * @return the sprint
         */
        public Sprint getSprint() {
            return sprint;
        }
        
        /**
         * 
         * @param sprint
         */
        public Issue.Builder setSprint(Sprint sprint) {
            if (sprint != null) {
                this.sprint = sprint;
            }
            return this;
        }

        /**
         * @return
         */
        public Issue build() {
            return new Issue(this.key, this.name, this.description,
                    this.status, this.priority, this.estimatedTime, this.player, this.sprint);
        }

    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Issue && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Issue that) {
        final int equal = 0;
        
        if (this == that) {
            return equal;
        }
        
        int comparison = this.getStatus().compareTo(that.getStatus());
        if (comparison != equal) {
            return comparison;
        }
        
        comparison = this.getPriority().compareTo(that.getPriority());
        if (comparison != equal) {
            return comparison;
        }
        
        comparison = this.getName().compareTo(that.getName());
        return comparison;
    }

}
