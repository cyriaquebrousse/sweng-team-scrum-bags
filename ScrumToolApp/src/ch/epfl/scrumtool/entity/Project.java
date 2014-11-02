package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;
import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author Vincent, zenhaeus
 * 
 */
public final class Project implements DatabaseInteraction<Project> {

    private final String id;
    private final String name;
    private final String description;
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
    private Project(String id, String name, String description,
            Set<Player> players, Set<MainTask> backlog, Set<Sprint> sprints) {
        super();
        if (id == null || name == null || description == null
                || players == null || backlog == null || sprints == null) {
            throw new NullPointerException("Project.Constructor");
        }

        // TODO check that admin in players
        this.id = id;
        this.name = name;
        this.description = description;
        this.players = new HashSet<Player>(players);
        this.backlog = new HashSet<MainTask>(backlog);
        this.sprints = new HashSet<Sprint>(sprints);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * @return the admin TODO
     */
    public Player getAdmin() {
        // TODO: query database to get admin
        return null;
    }

    /**
     * @return the players
     */
    public Set<Player> getPlayers() {
        return players;
    }

    /**
     * @return the backlog
     */
    public Set<MainTask> getBacklog() {
        return backlog;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the mSprints
     */
    public Set<Sprint> getSprints() {
        return sprints;
    }

    public int getChangesCount(User user) {
        // TODO implement changes count + javadoc
        Random r = new Random();
        return r.nextInt(20);
    }

    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        // TODO Database Call + javadoc
    	
        return Role.DEVELOPER;
    }

    /**
     * Builder class for Project object
     * 
     * @author zenhaeus
     *
     */
    public static class Builder {
        private String id;
        private String name;
        private String description;
        private final Set<Player> players;
        private final Set<MainTask> backlog;
        private final Set<Sprint> sprints;

        public Builder() {
            this.players = new HashSet<Player>();
            this.backlog = new HashSet<MainTask>();
            this.sprints = new HashSet<Sprint>();
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
         * @return the players
         */
        public Set<Player> getPlayers() {
            return players;
        }

        /**
         * @return the backlog
         */
        public Set<MainTask> getBacklog() {
            return backlog;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         *            the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the mSprints
         */
        public Set<Sprint> getSprints() {
            return sprints;
        }

        public int getChangesCount(User user) {
            // TODO implement changes count + javadoc
            return Math.abs(user.hashCode()) % 10;
        }

        public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
            // TODO Database Call + javadoc
            return Role.DEVELOPER;
        }
        
        public Project build() {
            return new Project(this.id, this.name, this.description, this.players, this.backlog, this.sprints);
        }
    }

    @Override
    public void updateDatabase(DatabaseHandler<Project> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.update(this);
        
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Project> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.remove(this);
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
        return id.hashCode();
    }

}
