/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public final class Issue extends AbstractTask {

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
        this.mPlayer = new Player(player);
    }

    /**
     * @param anIssue
     */
    public Issue(Issue anIssue) {
        this(anIssue.getId(), anIssue.getName(), anIssue.getDescription(),
                anIssue.getStatus(), anIssue.getEstimatedTime(), anIssue
                        .getPlayer());
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
        return new Player(mPlayer);
    }

    /**
     * @param mProgramer
     *            the mProgramer to set
     */
    public void setPlayer(Player player) {
        if (player != null) {
            this.mPlayer = new Player(player);
        }
    }

}
