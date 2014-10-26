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
     * @param sprints
     */
    public Project(long id, String name, String description, Player admin,
            Set<Player> players, Set<MainTask> backlog, Set<Sprint> sprints) {
        super();
        if (name == null || description == null || admin == null
                || players == null || backlog == null || sprints == null) {
            throw new NullPointerException("Project.Constructor");
        }

        // TODO check that admin in players + copie profonde admin
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
        // TODO copie profonde de admin
        return mAdmin;
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Player admin) {
        if (admin != null) {
            // TODO copie prodonde de admin
            this.mAdmin = admin;
        }
    }

    /**
     * @return the players
     */
    public Set<Player> getPlayers() {
        return new HashSet<Player>(mPlayers);
    }

    /**
     * @param player
     */
    public void addPlayer(Player player) {
        if (player != null) {
            // TODO copie profonde de player
            this.mPlayers.add(player);
        }
    }

    /**
     * @param player
     */
    public void removePlayer(Player player) {
        if (player != null) {
            this.mPlayers.remove(player);
        }
    }

    /**
     * @return the backlog
     */
    public Set<MainTask> getBacklog() {
        return new HashSet<MainTask>(mBacklog);
    }

    /**
     * @param task
     */
    public void addTask(MainTask task) {
        if (task != null) {
            // TODO copie profonde de MainTask
            this.mBacklog.add(task);
        }
    }

    /**
     * @param task
     */
    public void removeTask(MainTask task) {
        if (task != null) {
            this.mBacklog.remove(task);
        }
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
        return new HashSet<Sprint>(mSprints);
    }

    /**
     * @param sprint
     */
    public void addSprint(Sprint sprint) {
        if (sprint != null) {
            // TODO copie profonde Sprint
            this.mSprints.add(sprint);
        }
    }

    /**
     * @param sprint
     */
    public void removeSprint(Sprint sprint) {
        if (sprint != null) {
            this.mSprints.remove(sprint);
        }
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
