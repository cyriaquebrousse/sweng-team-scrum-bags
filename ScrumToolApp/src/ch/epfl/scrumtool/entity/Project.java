package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author Vincent
 * 
 */
public final class Project {

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

        // TODO check that admin in players + copie profonde sets
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mAdmin = new Player(admin);
        this.mPlayers = new HashSet<Player>();
        for (Player p : players) {
            mPlayers.add(new Player(p));
        }
        this.mBacklog = new HashSet<MainTask>();
        for (MainTask m : backlog) {
            mBacklog.add(new MainTask(m));
        }
        this.mSprints = new HashSet<Sprint>();
        for (Sprint s : sprints) {
            mSprints.add(new Sprint(s));
        }
    }

    /**
     * @param aProject
     */
    public Project(Project aProject) {
        this(aProject.getId(), aProject.getName(), aProject.getDescription(),
                aProject.getAdmin(), aProject.getPlayers(), aProject
                        .getBacklog(), aProject.getSprints());
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
        return new Player(mAdmin);
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Player admin) {
        if (admin != null) {
            this.mAdmin = new Player(admin);
        }
    }

    /**
     * @return the players
     */
    public Set<Player> getPlayers() {
        HashSet<Player> tmp = new HashSet<Player>();
        for (Player p : mPlayers) {
            tmp.add(new Player(p));
        }
        return tmp;
    }

    /**
     * @param player
     */
    public void addPlayer(Player player) {
        if (player != null) {
            this.mPlayers.add(new Player(player));
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
        HashSet<MainTask> tmp = new HashSet<MainTask>();
        for (MainTask m : mBacklog) {
            tmp.add(new MainTask(m));
        }
        return tmp;
    }

    /**
     * @param task
     */
    public void addTask(MainTask task) {
        if (task != null) {
            this.mBacklog.add(new MainTask(task));
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
        HashSet<Sprint> tmp = new HashSet<Sprint>();
        for (Sprint s : mSprints) {
            tmp.add(new Sprint(s));
        }
        return tmp;
    }

    /**
     * @param sprint
     */
    public void addSprint(Sprint sprint) {
        if (sprint != null) {
            this.mSprints.add(new Sprint(sprint));
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
        Random r = new Random();
        return r.nextInt(20);
    }

    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        // TODO Database Call + javadoc
        return Entity.getRandomRole();
    }
}
