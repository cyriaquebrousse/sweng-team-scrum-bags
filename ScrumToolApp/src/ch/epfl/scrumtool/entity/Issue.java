package ch.epfl.scrumtool.entity;

import java.io.Serializable;

/**
 * A class that represent an Issue (child of a Maintask)
 * 
 * @author Vincent, zenhaeus
 * 
 */
public final class Issue extends AbstractTask implements Serializable {

    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.ISSUE";
    private static final long serialVersionUID = -1590796103232831763L;

    private float estimatedTime;
    private Player player;

    /**
     * @param key
     * @param name
     * @param description
     * @param status
     * @param estimatedTime
     * @param player
     */
    private Issue(String key, String name, String description, Status status,
            float estimatedTime, Player player) {
        super(key, name, description, status);
        // if (player == null) {
        // throw new NullPointerException("Issue.Constructor");
        // }
        this.estimatedTime = estimatedTime;

        this.player = player;

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
        private float estimatedTime;
        private Player player;

        public Builder() {
        }

        public Builder(Issue otherIssue) {
            this.key = otherIssue.getKey();
            this.name = otherIssue.getName();
            this.description = otherIssue.getDescription();
            this.status = otherIssue.getStatus();
            this.estimatedTime = otherIssue.estimatedTime;
            this.player = otherIssue.player;
        }

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param id
         */
        public void setKey(String id) {
            this.key = id;
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
        public void setName(String name) {
            this.name = name;
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
        public void setDescription(String description) {
            this.description = description;
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
        public void setStatus(Status status) {
            this.status = status;
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
        public void setEstimatedTime(float estimatedTime) {
            this.estimatedTime = estimatedTime;
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
        public void setPlayer(Player player) {
            this.player = player;
        }

        /**
         * @return
         */
        public Issue build() {
            return new Issue(this.key, this.name, this.description,
                    this.status, this.estimatedTime, this.player);
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

}
