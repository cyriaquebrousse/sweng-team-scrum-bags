package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;

/**
 * @author Vincent, zenhaeus
 * 
 */
public final class Player implements DatabaseInteraction<Player> {
    private final String id;
    private final User user;
    private final Role role;
    private final boolean isAdmin;

    /**
     * @param user
     */
    private Player(String id, User user, Role role, boolean isAdmin) {
        super();
        if (id == null || user == null || role == null) {
            throw new NullPointerException("Player.Constructor");
        }
        this.id = id;
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
    public String getId() {
        return this.id;
    }
    
    /**
     * @return admin Flag
     */
    public boolean isAdmin() {
    	return this.isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        Player other = (Player) o;
        if (other.getId() != this.getId()) {
            return false;
        }
        if (other.getRole() != this.getRole()) {
            return false;
        }
        if (!other.getUser().equals(this.getUser())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
	}

    /**
     * Builder Class for Player Object
     * @author zenhaeus
     *
     */
    public static class Builder {
        private User user;
        private String id;
        private Role role;
        private boolean isAdmin;

        public Builder() {
        }
        
        public Builder(Player otherPlayer) {
            this.user = otherPlayer.user;
            this.id = otherPlayer.id;
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
        public void setUser(User user) {
            if (user != null) {
                this.user = user;
            }
        }

        /**
         * @return the role
         */
        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            if (role != null) {
                this.role = role;
            }
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
         * @return isAdmin
         */
        public boolean isAdmin() {
            return this.isAdmin;
        }
        
        /**
         * @param isAdmin
         */
        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }
        
        
        /**
         * Creates and returns a new immutable instance of Player
         * @return
         */
        public Player build() {
            return new Player(this.id, this.user, this.role, this.isAdmin);
        }
    }

    @Override
    public void updateDatabase(DatabaseHandler<Player> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.update(this, successCb);
        
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Player> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        handler.remove(this, successCb);
        
    }

}
