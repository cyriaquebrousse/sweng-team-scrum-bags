/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.io.IOException;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumuserendpoint.Scrumuserendpoint;
import ch.epfl.scrumtool.server.scrumuserendpoint.model.ScrumUser;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

/**
 * @author aschneuw
 *
 */
public class Session {
	public static final String CLIENT_ID = "756445222019-sefvcpr9aos0dg4r0unt44unvaqlg1nq.apps.googleusercontent.com";
	public static final int REQUEST_ACCOUNT_PICKER = 2;
	
	private GoogleAccountCredential googleCredential = null;
	private User scrumUser = null;
	
	public void authenticate(Activity loginContext) {
		googleCredential = GoogleAccountCredential.usingAudience(loginContext, "server:client_id:"+CLIENT_ID);
		Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
		loginContext.startActivityForResult(googleCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
		googleCredential.setSelectedAccountName((String) googleAccountPicker.getExtras().get(AccountManager.KEY_ACCOUNT_NAME));
		
		Scrumuserendpoint.Builder builder = new Scrumuserendpoint.Builder(AndroidHttp.newCompatibleTransport(),
				new GsonFactory(), null);
		builder.setRootUrl("http://192.168.10.20:8080/_ah/api");
		Scrumuserendpoint service = builder.build();
		
		ScrumUser test = new ScrumUser();
		test.setName(googleCredential.getSelectedAccountName());
		
		
		try {
			service.insertScrumUser(test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public GoogleAccountCredential getCurrenUser() { 
		return googleCredential;
	}
	
	public User getCurrentUser() {
		return scrumUser;
	}
	
	

}
