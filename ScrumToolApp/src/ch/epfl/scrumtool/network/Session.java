/**
 * 
 */
package ch.epfl.scrumtool.network;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.DSUserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.LoginActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * @author aschneuw
 * 
 */
public class Session {
    public static final String CLIENT_ID =
            "756445222019-sefvcpr9aos0dg4r0unt44unvaqlg1nq.apps.googleusercontent.com";
    public static final int REQUEST_ACCOUNT_PICKER = 2;

    private static GoogleAccountCredential googleCredential = null;
    private static User scrumUser = null;

    public static void authenticate(final GoogleAccountCredential credential, final LoginActivity loginContext) {
        googleCredential = credential;
        
        DSUserHandler handler = new DSUserHandler();
        String email = googleCredential.getSelectedAccountName();
        handler.loginUser(email, new Callback<User>() {

            @Override
            public void interactionDone(User object) {
                if (object != null) {
                    scrumUser = object;
                    loginContext.openMenuActivity();
                } else {
//                    TODO: Error handling
//                    throw new NotAuthenticatedException();
                }
            }
        });

    }

    public static GoogleAccountCredential getCurrenUser() throws NotAuthenticatedException {
        if (googleCredential == null) {
            throw new NotAuthenticatedException();
        } else {
            return googleCredential;
        }
    }

    public static User getCurrentUser() throws NotAuthenticatedException {
        if (scrumUser == null) {
            throw new NotAuthenticatedException();
        } else {
            return scrumUser;
        }
    }
}
