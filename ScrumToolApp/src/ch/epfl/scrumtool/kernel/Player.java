/**
 * 
 */
package ch.epfl.scrumtool.kernel;

/**
 * @author Vincent
 * 
 */
public abstract class Player {
    enum Role {
        PROGRAMMER, STAKEHOLDER
    };

    private User user;
    private Role role;

    /**
     * @param user
     * @param role
     */
    public Player(User user, Role role) {
        super();
        this.user = user;
        this.role = role;
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
        this.user = user;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

}
