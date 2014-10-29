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

    private long id;
    private String name;
    private String description;
    private Player admin;
    private final Set<Player> players;
    private final Set<MainTask> backlog;
    private final Set<Sprint> sprints;

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

        // TODO check that admin in players
        this.id = id;
        this.name = name;
        this.description = description;
        this.admin = new Player(admin);
        this.players = new HashSet<Player>();
        for (Player p : players) {
            this.players.add(new Player(p));
        }
        this.backlog = new HashSet<MainTask>();
        for (MainTask m : backlog) {
            this.backlog.add(new MainTask(m));
        }
        this.sprints = new HashSet<Sprint>();
        for (Sprint s : sprints) {
            this.sprints.add(new Sprint(s));
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
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    /**
     * @return the admin
     */
    public Player getAdmin() {
        return new Player(admin);
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Player admin) {
        if (admin != null) {
            this.admin = new Player(admin);
        }
    }

    /**
     * @return the players
     */
    public Set<Player> getPlayers() {
        HashSet<Player> tmp = new HashSet<Player>();
        for (Player p : players) {
            tmp.add(new Player(p));
        }
        return tmp;
    }

    /**
     * @param player
     */
    public void addPlayer(Player player) {
        if (player != null) {
            this.players.add(new Player(player));
        }
    }

    /**
     * @param player
     */
    public void removePlayer(Player player) {
        if (player != null) {
            this.players.remove(player);
        }
    }

    /**
     * @return the backlog
     */
    public Set<MainTask> getBacklog() {
        HashSet<MainTask> tmp = new HashSet<MainTask>();
        for (MainTask m : backlog) {
            tmp.add(new MainTask(m));
        }
        return tmp;
    }

    /**
     * @param task
     */
    public void addTask(MainTask task) {
        if (task != null) {
            this.backlog.add(new MainTask(task));
        }
    }

    /**
     * @param task
     */
    public void removeTask(MainTask task) {
        if (task != null) {
            this.backlog.remove(task);
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the mSprints
     */
    public Set<Sprint> getSprints() {
        HashSet<Sprint> tmp = new HashSet<Sprint>();
        for (Sprint s : sprints) {
            tmp.add(new Sprint(s));
        }
        return tmp;
    }

    /**
     * @param sprint
     */
    public void addSprint(Sprint sprint) {
        if (sprint != null) {
            this.sprints.add(new Sprint(sprint));
        }
    }

    /**
     * @param sprint
     */
    public void removeSprint(Sprint sprint) {
        if (sprint != null) {
            this.sprints.remove(sprint);
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project other = (Project) o;
        if (!other.getAdmin().equals(this.getAdmin())) {
            return false;
        }
        if (!other.getBacklog().equals(this.getBacklog())) {
            return false;
        }
        if (!other.getDescription().equals(this.getDescription())) {
            return false;
        }
        if (other.getId() != this.getId()) {
            return false;
        }
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        if (!other.getPlayers().equals(this.getPlayers())) {
            return false;
        }
        if (!other.getSprints().equals(this.getSprints())) {
            return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        return (int) (id
                + name.hashCode()
                + description.hashCode()
                + admin.hashCode()
                + players.hashCode()
                + backlog.hashCode()
                + sprints.hashCode()) % Integer.MAX_VALUE;
    }

}
