package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author Vincent
 * 
 */
public class Project {

    private long mId;
    private String mName;
    private String mDescription;
    private Player mAdmin;
    private final Set<Player> mPlayers;
    private final Set<MainTask> mBacklog;
    private final Set<Sprint> mSprints;

    /**
     * @param name
     * @param description
     * @param admin
     * @param players
     * @param backlog
     * @params sprints
     */
    public Project(long id, String name, String description, Player admin,
            Set<Player> players, Set<MainTask> backlog, Set<Sprint> sprints) {
        super();
        if (name == null || description == null || admin == null
                || players == null || backlog == null || sprints == null) {
            throw new NullPointerException("Project.Constructor");
        }

        // TODO check that admin in players
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mAdmin = admin;
        this.mPlayers = new HashSet<Player>(players);
        this.mBacklog = new HashSet<MainTask>(backlog);
        this.mSprints = new HashSet<Sprint>(sprints);
    }

    /**
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name != null) {
            this.mName = name;
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        if (description != null) {
            this.mDescription = description;
        }
    }

    /**
     * @return the admin
     */
    public Player getAdmin() {
        return mAdmin;
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Player admin) {
        if (admin != null) {
            this.mAdmin = admin;
        }
    }

    /**
     * @return the players
     */
    public Set<Player> getPlayers() {
        return mPlayers;
    }

    // TODO add/remove MainTask/Player/Sprint

    /**
     * @return the backlog
     */
    public Set<MainTask> getBacklog() {
        return mBacklog;
    }

    /**
     * @return the id
     */
    public long getId() {
        return mId;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.mId = id;
    }

    /**
     * @return the mSprints
     */
    public Set<Sprint> getSprints() {
        return mSprints;
    }

    public int getChangesCount(User user) {
        // TODO implement changes count + javadoc
        return Math.abs(user.hashCode()) % 10;
    }

    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        // TODO Database Call + javadoc
        return Entity.getRandomRole();
    }
}
