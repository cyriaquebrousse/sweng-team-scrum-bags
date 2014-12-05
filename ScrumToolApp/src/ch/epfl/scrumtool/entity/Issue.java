package ch.epfl.scrumtool.entity;

import static ch.epfl.scrumtool.entity.Status.FINISHED;
import static ch.epfl.scrumtool.entity.Status.IN_SPRINT;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_ESTIMATION;
import static ch.epfl.scrumtool.entity.Status.READY_FOR_SPRINT;
import static ch.epfl.scrumtool.util.Assertions.assertTrue;

import java.io.Serializable;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;

/**
 * A class that represents an Issue (child of a Maintask)
 * 
 * @author Vincent
 * @author zenhaeus
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
    public void update(final Issue ref, Callback<Void> callback) {
        Client.getScrumClient().updateIssue(this, ref, callback);
    }

    /**
     * Marks the issue as done or undone
     * 
     * @param finished
     *            true to mark as done, false to mark as undone
     * @param callback
     */
    public void markAsDone(boolean finished, Callback<Void> callback) {
        Issue.Builder builder = new Issue.Builder(this);
        builder.setStatus(finished ? Status.FINISHED : Status.READY_FOR_ESTIMATION);
        Issue updatedIssue = builder.build();
        updatedIssue.update(this, callback);
    }

    /**
     * Removes the issue from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Void> callback) {
        Client.getScrumClient().deleteIssue(this, callback);
    }

    /**
     * Add an issue to the sprint on the DS
     * 
     * @param sprint
     * @param callback
     */
    public void addToSprint(final Sprint sprint, final Callback<Void> callback) {
        Client.getScrumClient().addIssueToSprint(this, sprint, callback);
    }

    /**
     * Get new instance of Builder
     * 
     * @return
     */
    public Builder getBuilder() {
        return new Builder(this);
    }

    /**
     * A Builder for the Issue
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
            if (estimatedTime >= 0f) {
                this.estimatedTime = estimatedTime;
            }
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
            this.player = player;
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
            this.sprint = sprint;
            return this;
        }

        /**
         * @return
         */
        public Issue build() {
            return new Issue(this.key, this.name, this.description,
                    this.status, this.priority, this.estimatedTime,
                    this.player, this.sprint);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Issue)) {
            return false;
        }

        Issue otherIssue = (Issue) o;
        
        boolean playerEquals = false;
        if (this.player == null && otherIssue.player == null) {
            playerEquals = true;
        } else if (this.player == null && otherIssue.player != null
                || this.player != null && otherIssue.player == null) {
            playerEquals = false;
        } else if (this.player != null && otherIssue.player != null) {
            playerEquals = this.player.equals(otherIssue.player);
        }
        
        boolean sprintEquals = false;
        if (this.sprint == null && otherIssue.sprint == null) {
            sprintEquals = true;
        } else if (this.sprint == null && otherIssue.sprint != null
                || this.sprint != null && otherIssue.sprint == null) {
            sprintEquals = false;
        } else if (this.sprint != null && otherIssue.sprint != null) {
            sprintEquals = this.sprint.equals(otherIssue.sprint);
        }
        
        return super.equals(otherIssue)
                && this.estimatedTime == otherIssue.estimatedTime
                && playerEquals
                && sprintEquals;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Issue that) {
        if (that == null) {
            return 1;
        }

        int comparison = this.getStatus().compareTo(that.getStatus());
        if (comparison != 0) {
            return comparison;
        }

        comparison = this.getPriority().compareTo(that.getPriority());
        if (comparison != 0) {
            return comparison;
        }

        return this.getName().compareTo(that.getName());
    }

    /**
     * Simulates the new issue status for a given builder. It does not have any
     * side effects (e.g. server modifications, modifications on the arguments,
     * etc).<br>
     * See ScrumIssue#verifyAndSetStatus in the app engine project
     * 
     * @param issueBuilder
     *            the builder to simulate on. Cannot be {@code null}.
     * @return the simulated status
     */
    public static Status simulateNewStatusForEstimationAndSprint(final Issue issue) {
        assertTrue(issue != null);
        final Issue.Builder issueBuilder = issue.getBuilder();
        
        final float estimation = issueBuilder.getEstimatedTime();
        final Sprint sprint = issueBuilder.getSprint();
        final boolean isFinished = issueBuilder.getStatus() == FINISHED;

        if (isFinished) {
            return FINISHED;
        }

        if (Float.compare(estimation, 0f) <= 0) {
            return READY_FOR_ESTIMATION;
        } else {
            if (sprint == null) {
                return READY_FOR_SPRINT;
            } else {
                return IN_SPRINT;
            }
        }
    }
}
