package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.network.Client;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
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
    public void update(final Project ref, final Callback<Boolean> callback) {
        Client.getScrumClient().updateProject(this, ref, callback);
    }

    /**
     * Deletes the project in the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Boolean> callback) {
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
    // FIXME should this really take a DefaultGUICallback as parameter and not just a Callback? (@zenhaeus)
    public void loadSprints(final DefaultGUICallback<List<Sprint>> defaultGUICallback) {
        Client.getScrumClient().loadSprints(this, defaultGUICallback);
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

    @Deprecated
    // TODO create a method in ScrumClient and delete this one.
    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        return Role.DEVELOPER;
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
        return other.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public int compareTo(Project that) {
        final int equal = 0;

        if (this == that) {
            return equal;
        }
        
        int comparison = this.getName().compareTo(that.getName());
        
        return comparison;
    }

}
