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
        // TODO copie profonde user( + role???)
        this.mId = id;
        this.mUser = user;
        this.mRole = role;
    }

    /**
     * @return the user
     */
    public User getUser() {
        // TODO copie profonde user
        return mUser;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        if (user != null) {
            // TODO copie profonde user
            this.mUser = user;
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
