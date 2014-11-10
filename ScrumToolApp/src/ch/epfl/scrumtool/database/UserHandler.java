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
    void loginUser(final String email, final Callback<User> cB);
}
