/**
 * 
 */
package ch.epfl.scrumtool.network;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import ch.epfl.scrumtool.database.DatabaseCallback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.google.DSProjectHandler;
import ch.epfl.scrumtool.database.google.DSUserHandler;
import ch.epfl.scrumtool.entity.Project;
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
        
        DatabaseHandler<User> handler = new DSUserHandler();
        handler.load("joey.zenhaeusern@epfl.ch", new DatabaseCallback<User>() {

			@Override
			public void interactionDone(User object) {
				scrumUser = object;
				currentSesstion = Session.this;
				
				Project.Builder pB = new Project.Builder();
				pB.setDescription("BLA");
				pB.setName("Test1 Scrumttool");
				Project p = pB.build();
				
				DatabaseHandler<Project> handler = new DSProjectHandler();
				handler.insert(p);
				
				
				
			}
		});
        
        
        
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
