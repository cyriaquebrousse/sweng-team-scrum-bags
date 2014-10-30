package ch.epfl.scrumtool.database.google;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

public class AppEngineUtils {
	private static final String APP_NAME = "ScrumTool";
    private static final String LOCAL_IP = "128.179.183.170";
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
            Scrumtool service = builder.build();
            return service;

        } else {
            return DB_SERVICE;
        }

    }

}
