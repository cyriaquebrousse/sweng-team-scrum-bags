package ch.epfl.scrumtool.database.google;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * The AppEngineUtils class provides constants and methods to configure 
 * access to the App Engine server locally and online.
 * 
 * @author aschneuw, zenhaeus
 * 
 */
public class AppEngineUtils {
    public static final String APP_NAME = "ScrumTool";
    private static final String LOCAL_IP = "10.0.0.22";
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
    private static final boolean TEST_LOCAL = false;

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
}
