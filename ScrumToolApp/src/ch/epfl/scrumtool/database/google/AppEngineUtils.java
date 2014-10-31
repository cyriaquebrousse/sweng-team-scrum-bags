package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

public class AppEngineUtils {
	private static final String APP_NAME = "ScrumTool";
    private static final String LOCAL_IP = "10.0.0.7";
    private static final String SERVER_URL = Scrumtool.DEFAULT_ROOT_URL;
    private static final String LOCAL_SERVER_URL = "http://" + LOCAL_IP
            + ":8888/_ah/api/";
    private static final boolean TEST_LOCAL = true;

    private static Scrumtool DB_SERVICE;

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
             * The following line fixes an issue with sending UTF-8 characters to the server
             * which caused an IllegalArgumentException
             * 
             * Source https://code.google.com/p/googleappengine/issues/detail?id=11057 post #17
             */
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
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
