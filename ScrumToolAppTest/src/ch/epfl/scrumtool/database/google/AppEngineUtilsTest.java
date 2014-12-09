package ch.epfl.scrumtool.database.google;

import junit.framework.TestCase;

public class AppEngineUtilsTest extends TestCase {
    
    public void testGetServerURL() {
        if (AppEngineUtils.TEST_LOCAL) {
            assertEquals(AppEngineUtils.LOCAL_SERVER_URL, AppEngineUtils.getServerURL());
        } else {
            assertEquals(AppEngineUtils.SERVER_URL, AppEngineUtils.getServerURL());
        }
    }

}
