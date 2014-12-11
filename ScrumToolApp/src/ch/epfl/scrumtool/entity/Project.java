package ch.epfl.scrumtool.entity;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * @author Vincent
 * @author zenhaeus
 */
public final class Project implements Serializable, Comparable<Project> {

    private static final long serialVersionUID = -4181818270822077982L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.PROJECT";

    private final String key;
    private final String name;
    private final String description;

    private Project(String key, String name, String description) {
        throwIfNull("Project constructor parameters cannot be null", key, name, description);
        Preconditions.throwIfEmptyString("A project must have a nonempty name", name);
        Preconditions.throwIfEmptyString("A project must contain a valid description", description);
        
        this.key = key;
        this.name = name;
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Creates the project in the DS
     * 
     * @param callback
     */
    public void insert(final Callback<Project> callback) {
        Client.getScrumClient().insertProject(this, callback);
    }

    /**
     * updates the project in the DS
     * 
     * @param ref
     * @param callback
     */
    public void update(final Callback<Void> callback) {
        Client.getScrumClient().updateProject(this, callback);
    }

    /**
     * Deletes the project in the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Void> callback) {
        Client.getScrumClient().deleteProject(this, callback);
    }

    /**
     * Adds a player to this project in the DS
     * 
     * @param userEmail
     * @param role
     * @param callback
     */
    public void addPlayer(final String userEmail, final Role role, final Callback<Player> callback) {
        Client.getScrumClient().addPlayerToProject(this, userEmail, role, callback);
    }

    /**
     * Loads the players from the DS
     * 
     * @param callback
     */
    public void loadPlayers(final Callback<List<Player>> callback) {
        Client.getScrumClient().loadPlayers(this, callback);
    }

    /**
     * Load the sprints of this project from the DS
     * 
     * @param defaultGUICallback
     */
    public void loadSprints(final Callback<List<Sprint>> callback) {
        Client.getScrumClient().loadSprints(this, callback);
    }

    /**
     * Load the backlog of this project from the DS
     * 
     * @param callback
     */
    public void loadBacklog(final Callback<List<MainTask>> callback) {
        Client.getScrumClient().loadBacklog(this, callback);
    }
    
    /**
     * Load all issues that are not yet assigned to any Sprint
     * 
     * @param callback
     */
    public void loadUnsprintedIssues(final Callback<List<Issue>> callback) {
        Client.getScrumClient().loadUnsprintedIssues(this, callback);
    }
    
    /**
     * 
     * @return Project.Builder
     */
    public Builder getBuilder() {
        return new Builder(this);
    }

    /**
     * Builder class for Project object
     * 
     * @author zenhaeus
     */
    public static class Builder {
        private String keyb;
        private String name;
        private String description;

        public Builder() {
            this.name = "";
            this.description = "";
            this.keyb = "";
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
        public Project.Builder setName(String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
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
        public Project.Builder setDescription(String description) {
            if (description != null) {
                this.description = description;
            }
            return this;
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
        public Project.Builder setKey(String id) {
            if (id != null) {
                this.keyb = id;
            }
            return this;
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
        return other.key.equals(this.key)
                && other.description.equals(this.description)
                && other.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public int compareTo(Project that) {
        if (that == null) {
            return 1;
        }
        return this.getName().compareTo(that.getName());
    }

}
