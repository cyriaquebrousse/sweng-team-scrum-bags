package ch.epfl.scrumtool.database.google;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
/**
 * 
 * @author aschneuw, zenhaeus
 *
 */
public class AppEngineUtils {
	public static final String APP_NAME = "ScrumTool";
    private static final String LOCAL_IP = "192.168.10.20";
    private static final String SERVER_URL = Scrumtool.DEFAULT_ROOT_URL;
    private static final String LOCAL_SERVER_URL = "http://" + LOCAL_IP
            + ":8888/_ah/api/";
    private static final boolean TEST_LOCAL = true;

    public static String getServerURL() {
        if (TEST_LOCAL) {
            return LOCAL_SERVER_URL;
        } else {
            return SERVER_URL;
        }
    }
}
