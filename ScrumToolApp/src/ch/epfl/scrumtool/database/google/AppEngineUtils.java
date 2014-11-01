package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

/**
 * The AppEngineUtils class provides constants and methods to configure 
 * access to the App Engine server locally and online.
 * 
 * @author zenhaeus
 * 
 */
public class AppEngineUtils {
    private static final String APP_NAME = "ScrumTool";
    private static final String LOCAL_IP = "10.0.0.";
    /**
     * URL to default Google App Engine project
     */
    private static final String SERVER_URL = Scrumtool.DEFAULT_ROOT_URL;
    /**
     * Full URL to our Google App Engine API
     */
    private static final String LOCAL_SERVER_URL = "http://" + LOCAL_IP
            + ":8888/_ah/api/";
    /**
     * If this is true the app will contact the local server
     */
    private static final boolean TEST_LOCAL = true;

    private static Scrumtool DB_SERVICE;

    /**
     * Returns server URL
     * If {@code}TEST_LOCAL is true then the local server url will be returned
     * else the url of the online App Engine is returned
     * 
     * @return
     */
    public static String getServerURL() {
        if (TEST_LOCAL) {
            return LOCAL_SERVER_URL;
        } else {
            return SERVER_URL;
        }
    }

    public static Scrumtool getServiceObject() {
        if (DB_SERVICE == null) {
            Scrumtool.Builder builder = new Scrumtool.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    null);
            builder.setRootUrl(AppEngineUtils.getServerURL());
            builder.setApplicationName(APP_NAME);
            /*
             * The following line fixes an issue with sending UTF-8 characters
             * to the server which caused an IllegalArgumentException
             * 
             * Source
             * https://code.google.com/p/googleappengine/issues/detail?id=11057
             * post #17
             */
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(
                        AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                        throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            Scrumtool service = builder.build();
            return service;

        } else {
            return DB_SERVICE;
        }

    }

}
