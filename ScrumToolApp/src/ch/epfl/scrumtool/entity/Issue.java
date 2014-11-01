/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public class Issue extends AbstractTask {

    private float mEstimatedTime;
    private Player mPlayer;

    /**
     * @param name
     * @param description
     * @param status
     * @param mEstimatedTime
     * @param mProgramer
     */
    public Issue(long id, String name, String description, Status status,
            float estimatedTime, Player player) {
        super(id, name, description, status);
        if (player == null) {
            throw new NullPointerException("Issue.Constructor");
        }
        this.mEstimatedTime = estimatedTime;
        this.mPlayer = player;
    }

    /**
     * @return the mEstimatedTime
     */
    public float getEstimatedTime() {
        return mEstimatedTime;
    }

    /**
     * @param mEstimatedTime
     *            the mEstimatedTime to set
     */
    public void setEstimatedTime(float mEstimatedTime) {
        this.mEstimatedTime = mEstimatedTime;
    }

    /**
     * @return the mProgramer
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /**
     * @param mProgramer
     *            the mProgramer to set
     */
    public void setPlayer(Player player) {
        if (player != null) {
            this.mPlayer = player;
        }
    }

    public static class Builder {
        private long id;
        private String name;
        private String description;
        private Status status;
        private float estimatedTime;
        private Player player;

        public Builder() {
        }

        public Builder(Issue otherIssue) {
            this.id = otherIssue.getId();
            this.name = otherIssue.getName();
            this.description = otherIssue.getDescription();
            this.status = otherIssue.getStatus();
            this.estimatedTime = otherIssue.mEstimatedTime;
            this.player = otherIssue.mPlayer;
        }

        /**
         * @return the id
         */
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the status
         */
        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        /**
         * @return the estimated time
         */
        public float getEstimatedTime() {
            return estimatedTime;
        }

        public void setEstimatedTime(float estimatedTime) {
            this.estimatedTime = estimatedTime;
        }

        /**
         * @return the player
         */
        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public Issue build() {
            return new Issue(this.id, this.name, this.description, this.status,
                    this.estimatedTime, this.player);
        }

    }

}
