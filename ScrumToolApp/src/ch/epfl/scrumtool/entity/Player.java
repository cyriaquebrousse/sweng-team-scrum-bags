/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public final class Player {
    private User mUser;
    private long mId;
    private Role mRole;

    /**
     * @param user
     */
    public Player(long id, User user, Role role) {
        super();
        if (user == null || role == null) {
            throw new NullPointerException("Player.Constructor");
        }
        this.mId = id;
        this.mUser = new User(user);
        this.mRole = role;
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
        return new User(mUser);
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        if (user != null) {
            this.mUser = new User(user);
        }
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        if (role != null) {
            this.mRole = role;
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return mId;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.mId = id;
    }

}
