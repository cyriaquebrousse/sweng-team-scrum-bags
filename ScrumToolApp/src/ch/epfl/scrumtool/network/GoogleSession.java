/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.io.IOException;

import android.accounts.AccountManager;
import android.content.Intent;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.AppEngineUtils;
import ch.epfl.scrumtool.database.google.DSUserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.LoginActivity;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

/**
 * @author aschneuw
 * 
 */
public class GoogleSession extends Session {
    public static final String CLIENT_ID = "756445222019-sefvcpr9aos0dg4r0unt44unvaqlg1nq.apps.googleusercontent.com";
    private static Scrumtool dbService = null;
    private Scrumtool authDBService = null;

    private final GoogleAccountCredential googleCredential;

    public GoogleSession(User user, GoogleAccountCredential credential) {
        super(user);
        this.googleCredential = credential;
    }

    public Scrumtool getAuthServiceObject() {
        // TODO
        // A pool of service objects???
        if (authDBService == null) {
            authDBService = getServiceObject(googleCredential);
        }

        return authDBService;
    }

    public static Scrumtool getServiceObject() {
        if (dbService == null) {
            dbService = getServiceObject(null);
        }
        return dbService;
    }

    private static Scrumtool getServiceObject(GoogleAccountCredential credential) {
        Scrumtool.Builder builder = new Scrumtool.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                credential);
        builder.setRootUrl(AppEngineUtils.getServerURL());
        builder.setApplicationName(AppEngineUtils.APP_NAME);
        /*
         * The following line fixes an issue with sending UTF-8 characters to
         * the server which caused an IllegalArgumentException
         * 
         * Source
         * https://code.google.com/p/googleappengine/issues/detail?id=11057 post
         * #17
         */
        builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(
                    AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
        });
        Scrumtool service = builder.build();
        return service;

    }

    public static class Builder {
        private GoogleAccountCredential googleCredential = null;
        private LoginActivity context = null;

        public Intent getIntent(LoginActivity context) {
            googleCredential = GoogleAccountCredential.usingAudience(context,
                    "server:client_id:" + GoogleSession.CLIENT_ID);

            this.context = context;
            return googleCredential.newChooseAccountIntent();
        }

        public void authenticate(Intent data) {
            DSUserHandler handler = new DSUserHandler();
            googleCredential.setSelectedAccountName(
                    (String) data.getExtras().get(AccountManager.KEY_ACCOUNT_NAME));
            String email = googleCredential.getSelectedAccountName();
            handler.loginUser(email, new Callback<User>() {

                @Override
                public void interactionDone(User object) {
                    if (object != null) {
                        new GoogleSession(object, googleCredential);
                        context.openOptionsMenu();
                    } else {
                        // TODO: Error handling
                        // throw new NotAuthenticatedException();
                    }
                }
            });
        }
    }
}
