package ch.epfl.scrumtool.entity;

import java.io.Serializable;

/**
 * @author Vincent, zenhaeus
 * 
 */
public final class Player implements Serializable {

    private static final long serialVersionUID = -1373129649658028177L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.PLAYER";

    private final String key;
    private final User user;
    private final Role role;
    private final boolean isAdmin;

    /**
     * @param user
     */
    private Player(String key, User user, Role role, boolean isAdmin) {
        super();
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        } 
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        } 
        if (role == null) {
            throw new NullPointerException("Role cannot be null");
        }
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
     * @return admin Flag
     */
    public boolean isAdmin() {
        return this.isAdmin;
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

    /**
     * Builder Class for Player Object
     * 
     * @author zenhaeus
     * 
     */
    public static class Builder {
        private User user;
        private String keyb;
        private Role role;
        private boolean isAdmin;

        /**
         * 
         */
        public Builder() {
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
            this.keyb = id;
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

}
