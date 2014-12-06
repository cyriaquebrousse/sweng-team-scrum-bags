package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

/**
 * @author Vincent
 * @author zenhaeus
 */
public final class Player implements Serializable, Comparable<Player> {

    private static final long serialVersionUID = -1373129649658028177L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.PLAYER";

    private final String key;
    private final User user;
    private final Project project;
    private final Role role;
    private final boolean isAdmin;
    private final boolean isInvited;

    private Player(String key, User user, Project project, Role role, boolean isAdmin, boolean isInvited) {
        throwIfNull("Player constructor parameters cannot be null", key, user, role);
        if (isAdmin && isInvited) {
            throw new IllegalStateException("An invited player can't be a project administrator");
        }
        
        this.key = key;
        this.user = user;
        this.project = project;
        this.role = role;
        this.isAdmin = isAdmin;
        this.isInvited = isInvited;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return this.user;
    }
    
    /**
     * @return the project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * @return the id
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return admin flag
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }
    
    
    /**
     * @return isInvited
     */
    public boolean isInvited() {
        return this.isInvited;
    }

    /**
     * Update the player in the DS
     * 
     * @param ref
     * @param callback
     */
    public void update(final Player ref, final Callback<Void> callback) {
        Client.getScrumClient().updatePlayer(this, ref, callback);
    }

    /**
     * Removes the player from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Void> callback) {
        Client.getScrumClient().removePlayer(this, callback);
    }
    
    /**
     * Get new instance of Builder
     * @return
     */
    public Builder getBuilder() {
        return new Builder(this);
    }


    /**
     * Builder Class for Player Object
     * 
     * @author zenhaeus
     */
    public static class Builder {
        private User user;
        private String keyb;
        private Role role;
        private Project project;
        private boolean isAdmin;
        private boolean isInvited;
        
        public Builder() {
            this.isAdmin = false;
            this.isInvited = true;
            this.role = Role.INVITED;
            this.keyb = "";
        }

        /**
         * @param otherPlayer
         */
        public Builder(Player otherPlayer) {
            this.user = otherPlayer.user;
            this.project = otherPlayer.project;
            this.keyb = otherPlayer.key;
            this.role = otherPlayer.role;
            this.isAdmin = otherPlayer.isAdmin;
            this.isInvited = otherPlayer.isInvited;
        }

        /**
         * @return the user
         */
        public User getUser() {
            return user;
        }

        /**
         * @param user
         *            the user to set
         */
        public Player.Builder setUser(User user) {
            if (user != null) {
                this.user = user;
            }
            return this;
        }
        
        /**
         * @return the project
         */
        public Project getProject() {
            return this.project;
            
        }
        
        /**
         * @param project
         * @return the builder
         */
        public Player.Builder setProject(Project project) {
            if (project != null) {
                this.project = project;
            }
            return this;
        }

        /**
         * @return the role
         */
        public Role getRole() {
            return role;
        }

        /**
         * @param role
         */
        public Player.Builder setRole(Role role) {
            if (role != null) {
                this.role = role;
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
        public Player.Builder setKey(String id) {
            if (id != null) {
                this.keyb = id;
            }
            return this;
        }

        /**
         * @return isAdmin
         */
        public boolean isAdmin() {
            return this.isAdmin;
        }

        /**
         * @param isAdmin
         */
        public Player.Builder setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }
        
        /**
         * @param isInvited
         * @return the builder
         */
        public Player.Builder setIsInvited(boolean isInvited) {
            this.isInvited = isInvited;
            return this;
        }

        /**
         * @return isInvited
         */
        public boolean isInvited() {
            return this.isInvited;
        }
        /**
         * Creates and returns a new immutable instance of Player
         * 
         * @return
         */
        public Player build() {
            return new Player(this.keyb, this.user, this.project, this.role, this.isAdmin, this.isInvited);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Player)) {
            return false;
        }
        Player other = (Player) o;
        return other.key.equals(this.key)
                && other.isAdmin == this.isAdmin
                && other.role == this.role
                && other.project == this.project
                && other.user == this.user
                && other.isInvited == this.isInvited;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public int compareTo(Player that) {
        if (that == null) {
            return 1;
        }

        int comparison = this.getUser().compareTo(that.getUser());
        if (comparison != 0) {
            return comparison;
        }

        return this.getRole().compareTo(that.getRole());
    }
}
