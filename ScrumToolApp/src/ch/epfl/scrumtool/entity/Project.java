package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author Vincent, zenhaeus
 * 
 */
public class Project {

    private final long id;
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
     * @params sprints
     */
    private Project(long id, String name, String description,
            Set<Player> players, Set<MainTask> backlog, Set<Sprint> sprints) {
        super();
        if (name == null || description == null
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
        return description;
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
    public long getId() {
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
        return Math.abs(user.hashCode()) % 10;
    }

    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        // TODO Database Call + javadoc
        return Entity.getRandomRole();
    }

    public static class Builder {
        private long id;
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

        // TODO add/remove MainTask/Player/Sprint

        /**
         * @return the backlog
         */
        public Set<MainTask> getBacklog() {
            return backlog;
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
            return sprints;
        }

        public int getChangesCount(User user) {
            // TODO implement changes count + javadoc
            return Math.abs(user.hashCode()) % 10;
        }

        public Role getRoleFor(User user)
                throws NotAPlayerOfThisProjectException {
            // TODO Database Call + javadoc
            return Entity.getRandomRole();
        }
        
        public Project build() {
            return new Project(this.id, this.name, this.description, this.players, this.backlog, this.sprints);
        }
    }
}
