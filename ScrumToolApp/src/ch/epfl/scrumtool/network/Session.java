/**
 * 
 */
package ch.epfl.scrumtool.network;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;

/**
 * @author aschneuw
 * 
 */
public abstract class Session {
    private static Session currentSession = null;

    private final User user;

    public Session(User user) {
        if (user == null) {
            throw new NullPointerException("A session must have a valid user");
        }
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public static Session getCurrentSession() throws NotAuthenticatedException {
        if (currentSession == null) {
            throw new NotAuthenticatedException();
        } else {
            return currentSession;
        }
    }
}
