/**
 * 
 */
package ch.epfl.scrumtool.network;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import ch.epfl.scrumtool.database.DatabaseCallback;
import ch.epfl.scrumtool.database.google.DSUserHandler;
import ch.epfl.scrumtool.entity.User;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * @author aschneuw
 * 
 */
public class Session {
    public static final String CLIENT_ID = "756445222019-sefvcpr9aos0dg4r0unt44unvaqlg1nq.apps.googleusercontent.com";
    public static final int REQUEST_ACCOUNT_PICKER = 2;

    private GoogleAccountCredential googleCredential = null;
    private User scrumUser = null;

    private static Session currentSesstion;

    public void authenticate(Activity loginContext) {
        googleCredential = GoogleAccountCredential.usingAudience(loginContext,
                "server:client_id:" + CLIENT_ID);
        Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
        loginContext.startActivityForResult(
                googleCredential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
        googleCredential.setSelectedAccountName((String) googleAccountPicker
                .getExtras().get(AccountManager.KEY_ACCOUNT_NAME));

        User.Builder uB = new User.Builder();
        uB.setName("Arno");
        uB.setEmail("arno.schneuwly@epfl.ch");

        DSUserHandler uH = new DSUserHandler();
        uH.insert(uB.build());

        uH.load(uB.getEmail(), new DatabaseCallback<User>() {

            @Override
            public void interactionDone(User object) {
                String name = object.getEmail();
                Log.d("GET", name);

            }
        });
        //
        // User user = uB.build();
        // DatabaseHandler<User> handler = new GoogleUserHandler();
        // handler.insert(user);

        // User arno = handler.get("arno.schneuwly@epfl.ch");

        // int n = Role.PRODUCT_OWNER.ordinal();
        // AuthenticateTask newTask = new AuthenticateTask();
        // newTask.execute();
    }
    public static Session getCurrentSession() {
        return currentSesstion;
    }
    public GoogleAccountCredential getCurrenUser() {
        return googleCredential;
    }

    public User getCurrentUser() {
        return scrumUser;
    }
}
