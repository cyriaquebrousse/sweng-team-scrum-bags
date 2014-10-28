/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public final class Issue extends AbstractTask {

    private float estimatedTime;
    private Player player;

    /**
     * @param name
     * @param description
     * @param status
     * @param estimatedTime
     * @param mProgramer
     */
    public Issue(long id, String name, String description, Status status,
            float estimatedTime, Player player) {
        super(id, name, description, status);
        if (player == null) {
            throw new NullPointerException("Issue.Constructor");
        }
        this.estimatedTime = estimatedTime;
        this.player = new Player(player);
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
        return estimatedTime;
    }

    /**
     * @param mEstimatedTime
     *            the mEstimatedTime to set
     */
    public void setEstimatedTime(float mEstimatedTime) {
        this.estimatedTime = mEstimatedTime;
    }

    /**
     * @return the mProgramer
     */
    public Player getPlayer() {
        return new Player(player);
    }

    /**
     * @param mProgramer
     *            the mProgramer to set
     */
    public void setPlayer(Player player) {
        if (player != null) {
            this.player = new Player(player);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        // TODO equals player and not ==
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) o;
        if (other.getEstimatedTime() != this.getEstimatedTime()) {
            return false;
        }
        if (!other.getPlayer().equals(this.getPlayer())) {
            return false;
        }
        return super.equals(o);
    }

}
