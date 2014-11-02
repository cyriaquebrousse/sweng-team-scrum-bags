package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;

/**
 * @author Vincent, zenhaeus
 * 
 */
public final class Issue extends AbstractTask implements DatabaseInteraction<Issue> {
    private float estimatedTime;
    private Player player;

    /**
     * @param id
     * @param name
     * @param description
     * @param status
     * @param estimatedTime
     * @param player
     */
    private Issue(String id, String name, String description, Status status,
            float estimatedTime, Player player) {
        super(id, name, description, status);
        if (player == null) {
            throw new NullPointerException("Issue.Constructor");
        }
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
     * @author 
     *
     */
    public static class Builder {
        private String id;
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
            this.estimatedTime = otherIssue.estimatedTime;
            this.player = otherIssue.player;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
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

    @Override
    public void updateDatabase(DatabaseHandler<Issue> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Issue> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        
    }

}
