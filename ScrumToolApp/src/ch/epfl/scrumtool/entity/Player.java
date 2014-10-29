/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public final class Player {
    private User user;
    private long id;
    private Role role;

    /**
     * @param user
     */
    public Player(long id, User user, Role role) {
        super();
        if (user == null || role == null) {
            throw new NullPointerException("Player.Constructor");
        }
        this.id = id;
        this.user = new User(user);
        this.role = role;
    }

    /**
     * @param aPlayer
     */
    public Player(Player aPlayer) {
        this(aPlayer.getId(), aPlayer.getUser(), aPlayer.getRole());
    }

    /**
     * @return the user
     */
    public User getUser() {
        return new User(user);
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        if (user != null) {
            this.user = new User(user);
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
        return (int) (id + role.hashCode() + user.hashCode()) % Integer.MAX_VALUE;
    }

}
