package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Vincent, zenhaeus
 */
public final class Project implements Serializable {

    private static final long serialVersionUID = -4181818270822077982L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.PROJECT";

    private final String key;
    private final String name;
    private final String description;

    /**
     * @param name
     * @param description
     * @param admin
     */
    private Project(String key, String name, String description) {
        super();
        if (key == null || name == null || description == null) {
            throw new NullPointerException("Project.Constructor");
        }

        // TODO check that admin in players
        this.key = key;
        this.name = name;
        this.description = description;
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
     * @return the admin
     */
    public Player getAdmin() {
        // TODO query database to get admin
        return null;
    }

    /**
     * Load the Players of this Project
     * 
     * @param callback
     */
    public void loadPlayers(Callback<List<Player>> callback) {
        Client.getScrumClient().loadPlayers(this, callback);
    }

    /**
     * Load the Backlog of this Project
     * 
     * @param callback
     */
    public void loadBacklog(Callback<List<MainTask>> callback) {
        Client.getScrumClient().loadBacklog(this, callback);
    }

    /**
     * Load the Sprints of this project
     * 
     * @param callback
     */
    public void loadSprints(Callback<List<Sprint>> callback) {
        Client.getScrumClient().loadSprints(this, callback);
    }

    // TODO save and remove methods for collections (same style as load methods
    // above)

    /**
     * @return the id
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the number of changes since last time seen
     * 
     * @param user
     * @return
     */
    public int getChangesCount(User user) {
        // TODO implement changes count + javadoc
        Random r = new Random();
        final int twenty = 20;
        return r.nextInt(twenty);
    }

    /**
     * Gets the role of a User in the project
     * 
     * @param user
     * @return
     * @throws NotAPlayerOfThisProjectException
     */
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
        private String keyb;
        private String name;
        private String description;

        public Builder() {
            this.name = "";
            this.description = "";
        }

        public Builder(Project other) {
            this.keyb = other.key;
            this.name = other.name;
            this.description = other.description;
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
         * @return the id
         */
        public String getKey() {
            return keyb;
        }

        /**
         * @param id
         *            the id to set
         */
        public void setKey(String id) {
            this.keyb = id;
        }

        public Project build() {
            return new Project(this.keyb, this.name, this.description);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Project)) {
            return false;
        }
        Project other = (Project) o;
        return other.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
