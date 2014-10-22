package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public class Player implements PlayerInterface {

    private UserInterface user;
    private Role role;

    /**
     * @param user
     * @param role
     */
    public Player(UserInterface user, Role role) {
        super();
        this.user = user;
        this.role = role;
    }


    @Override
    public UserInterface getAccount() {
        return user;
    }

    /**
     * @param user
     */
    public void setAccount(UserInterface user) {
        this.user = user;
    }

    @Override
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

}
