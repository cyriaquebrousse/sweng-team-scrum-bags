/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.io.IOException;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import ch.epfl.scrumtool.entity.DatabaseHandler;
import ch.epfl.scrumtool.entity.GoogleUserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

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
        googleCredential = GoogleAccountCredential.usingAudience(loginContext,
                "server:client_id:" + CLIENT_ID);
        Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
        loginContext.startActivityForResult(
                googleCredential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
        googleCredential.setSelectedAccountName((String) googleAccountPicker
                .getExtras().get(AccountManager.KEY_ACCOUNT_NAME));
//        User.Builder uB = new User.Builder();
//        uB.setName("Joey");
//        uB.setEmail("joey.zenhausern@epfl.ch");
//
//        User user = uB.build();
        DatabaseHandler<User> handler = new GoogleUserHandler();
//        handler.insert(user);

         User arno = handler.get("arno.schneuwly@epfl.ch");


        // AuthenticateTask newTask = new AuthenticateTask();
        // newTask.execute();

    }

    public GoogleAccountCredential getCurrenUser() {
        return googleCredential;
    }

    public User getCurrentUser() {
        return scrumUser;
    }

    class AuthenticateTask extends AsyncTask<Void, Void, Void> {

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(Void... params) {
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(Scrumtool.DEFAULT_ROOT_URL);
            Scrumtool service = builder.build();

            ScrumUser test = new ScrumUser();
            test.setEmail("test@gmail.com");
            test.setName("Tester");

            try {
                service.insertScrumUser(test).execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                Log.d("INSERT", "success");
            }

            return null;
        }

    }

}
