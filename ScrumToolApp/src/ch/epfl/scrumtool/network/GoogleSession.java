package ch.epfl.scrumtool.network;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandlers;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.AppEngineUtils;
import ch.epfl.scrumtool.database.google.handlers.DSIssueHandler;
import ch.epfl.scrumtool.database.google.handlers.DSMainTaskHandler;
import ch.epfl.scrumtool.database.google.handlers.DSPlayerHandler;
import ch.epfl.scrumtool.database.google.handlers.DSProjectHandler;
import ch.epfl.scrumtool.database.google.handlers.DSSprintHandler;
import ch.epfl.scrumtool.database.google.handlers.DSUserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.LoginActivity;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.util.Preconditions;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

/**
 * This class provides ways to create and access GoogleSessions, 
 * which are needed to access the Datastore on the Google App Engine
 * 
 * @author aschneuw
 */
public final class GoogleSession extends Session {
    private final Scrumtool service;
    
    private GoogleSession(User user, Scrumtool service) {
        super(user);
        Preconditions.throwIfNull("A session needs a valid ScrumTool service objects", service);
        this.service = service;
    }

    /**
     * @return Scrumtool
     * Returns a new Scrumtool service for access to API methods that do not
     * require authentication.
     */
    public Scrumtool getAuthServiceObject() {
        return service;
    }
    
    public static Scrumtool getServiceObject() {
        return Builder.generateServiceObject(null);
    }

    /**
     * This class provides functionalities to create a new GoogleSession
     * 
     * @author aschneuw
     *
     */
    public static class Builder {
        private static final String CLIENT_ID =
                "756445222019-7ll8hq36aorbfbno1unp49ikle7kt1nv.apps.googleusercontent.com";
        private final GoogleAccountCredential googleCredential;
        private final UserHandler handler;
        private final Context context;
        
        /**
         * Constructor for Google Session Builder
         * @param context
         */
        public Builder(LoginActivity context, UserHandler handler) {
            googleCredential = GoogleAccountCredential.usingAudience(context,
                    "server:client_id:" + GoogleSession.Builder.CLIENT_ID);
            Preconditions.throwIfNull("Needs a valid user handler for the login process", handler);
            this.handler = handler;
            this.context = context;
        }
        
        private static Scrumtool generateServiceObject(GoogleAccountCredential credential) {
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

        /**
         * @return
         */
        public Intent getIntent() {
            return googleCredential.newChooseAccountIntent();
        }

        /**
         * Create a new GoogleSession
         * @param accountName
         * @param authCallback
         */
        public void build(final String accountName, final Callback<Void> authCallback) {
            googleCredential.setSelectedAccountName(accountName);
            /*
             * If the server successfully logs in the user (insert user in case of 
             * non-existence, otherwise return user that wants to login) then the
             * interactionDone function of the Callback is executed.
             */
            
            final Scrumtool service = generateServiceObject(googleCredential);
            final User user = new User.Builder()
                .setEmail(accountName)
                .build();
            
            final GoogleSession session = new GoogleSession(user, service);
            
            handler.loginUser(accountName, new Callback<User>() {

                @Override
                public void interactionDone(User user) {
                        session.setUser(user);
                        DatabaseHandlers.Builder handlersBuilder = new DatabaseHandlers.Builder()
                            .setIssueHandler(new DSIssueHandler())
                            .setMaintaskHandler(new DSMainTaskHandler())
                            .setPlayerHandler(new DSPlayerHandler())
                            .setProjectHandler(new DSProjectHandler())
                            .setSprintHandler(new DSSprintHandler())
                            .setUserHandler(new DSUserHandler());

                        Client.setScrumClient(new DatabaseScrumClient(handlersBuilder.build()));
                        authCallback.interactionDone(null);
                }

                @Override
                public void failure(String errorMessage) {
                    authCallback.failure(errorMessage);
                    Client.setScrumClient(null);
                    Session.destroyCurrentSession(context);
                }
            });
        }
    }
}
