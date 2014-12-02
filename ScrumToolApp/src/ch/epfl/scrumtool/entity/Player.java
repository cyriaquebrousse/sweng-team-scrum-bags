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
    private final Role role;
    private final boolean isAdmin;

    private Player(String key, User user, Role role, boolean isAdmin) {
        throwIfNull("Player constructor parameters cannot be null", key, user, role);
        
        this.key = key;
        this.user = user;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return this.user;
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
     * Update the player in the DS
     * 
     * @param ref
     * @param callback
     */
    public void update(final Player ref, final Callback<Boolean> callback) {
        Client.getScrumClient().updatePlayer(this, ref, callback);
    }

    /**
     * Removes the player from the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Boolean> callback) {
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
        private boolean isAdmin;

        public Builder() {
            this.isAdmin = false;
            this.role = Role.INVITED;
            this.keyb = "";
        }

        /**
         * @param otherPlayer
         */
        public Builder(Player otherPlayer) {
            this.user = otherPlayer.user;
            this.keyb = otherPlayer.key;
            this.role = otherPlayer.role;
            this.isAdmin = otherPlayer.isAdmin;
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
         * Creates and returns a new immutable instance of Player
         * 
         * @return
         */
        public Player build() {
            return new Player(this.keyb, this.user, this.role, this.isAdmin);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Player)) {
            return false;
        }
        Player other = (Player) o;
        return other.key.equals(this.key);
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
