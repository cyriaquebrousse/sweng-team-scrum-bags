/**
 * 
 */
package ch.epfl.scrumtool.network;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.google.DSIssueHandler;
import ch.epfl.scrumtool.database.google.DSProjectHandler;
import ch.epfl.scrumtool.database.google.DSUserHandler;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Status;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;

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

    public static void authenticate(final Activity loginContext) {
        googleCredential = GoogleAccountCredential.usingAudience(loginContext,"server:client_id:" + CLIENT_ID);
        Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
        loginContext.startActivityForResult(
                googleCredential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
        googleCredential.setSelectedAccountName((String) googleAccountPicker
                .getExtras().get(AccountManager.KEY_ACCOUNT_NAME));

        DatabaseHandler<User> handler = new DSUserHandler();
        handler.loginUser(googleCredential.getSelectedAccountName(), new Callback<User>() {

            @Override
            public void interactionDone(User object) {
                if(object != null) {
                    scrumUser = object;
                    loginContext.openOptionsMenu();
                } else {
                    throw new NotAuthenticatedException();
                }
            }
        });

    }

    public static GoogleAccountCredential getCurrenUser() throws NotAuthenticatedException {
        if(googleCredential == null) {
            throw new NotAuthenticatedException();
        } else {
            return googleCredential;
        }
    }

    public static User getCurrentUser() throws NotAuthenticatedException {
        if(scrumUser == null) {
            throw new NotAuthenticatedException();
        } else {
            return scrumUser;
        }
    }
}
