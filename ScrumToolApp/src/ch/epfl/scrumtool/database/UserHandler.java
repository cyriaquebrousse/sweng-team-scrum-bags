/**
 * 
 */
package ch.epfl.scrumtool.database;

import ch.epfl.scrumtool.entity.User;

/**
 * @author aschneuw
 * 
 */
public interface UserHandler extends DatabaseHandler<User> {

    /**
     * Login the user corresponding to the given email address
     * 
     * @param email
     *            the user to log in
     * @param cB
     *            the fuction to call when the user is logged in
     */
    void loginUser(final String email, final Callback<User> cB);
}
