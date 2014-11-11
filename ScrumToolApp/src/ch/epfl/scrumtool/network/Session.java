package ch.epfl.scrumtool.network;

import android.app.Activity;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.settings.ApplicationSettings;

/**
 * @author aschneuw
 * 
 */
public abstract class Session {
    private static Session currentSession = null;

    private final User user;

    /**
     * @param user
     */
    protected Session(User user) {
        if (user == null) {
            throw new NullPointerException("A session must have a valid user");
        }
        this.user = user;
        currentSession = this;
    }

    /**
     * @return User
     *  Returns the user of this Session
     */
    public User getUser() {
        return user;
    }

    /**
     * @return Session
     *  Returns the Session the application is currently working with.
     * @throws NotAuthenticatedException
     */
    public static Session getCurrentSession() throws NotAuthenticatedException {
        if (currentSession == null) {
            throw new NotAuthenticatedException();
        } else {
            return currentSession;
        }
    }
    
    /**
     * Destroys current session
     * @param context
     */
    public static void destroyCurrentSession(Activity context) {
        /*
         *  Remove our user from the settings, otherwise the AccountPicker will be 
         *  skipped in the LoginActivity
         */
        ApplicationSettings.removeCachedUser(context);
        currentSession = null;
    }
}
