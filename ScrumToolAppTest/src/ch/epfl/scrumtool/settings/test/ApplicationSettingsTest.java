package ch.epfl.scrumtool.settings.test;

import org.junit.Test;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.scrumtool.gui.LoginActivity;
import ch.epfl.scrumtool.settings.ApplicationSettings;

public class ApplicationSettingsTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private final String preferences = "PrefsFile";
    private final String userPrefKey = "userPref";
    private final String userAccount = "test@account.com";

    private Context context = null;

    public ApplicationSettingsTest() {
        super(LoginActivity.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        context = getActivity();
    }


    @Test
    public void testGetCachedUser() {
        SharedPreferences settings = context.getSharedPreferences(preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(userPrefKey, userAccount);
        editor.commit();
        
        assertEquals(userAccount, ApplicationSettings.getCachedUser(context));
    }

    @Test
    public void testRemoveCachedUser() {
        SharedPreferences settings = context.getSharedPreferences(preferences, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(userPrefKey, userAccount);
        editor.commit();
        
        ApplicationSettings.removeCachedUser(context);
        
        assertNull(settings.getString(userPrefKey, null));
    }

    @Test
    public void testSaveCachedUser() {
        ApplicationSettings.saveCachedUser(context, userAccount);
        
        SharedPreferences settings = context.getSharedPreferences(preferences, 0);
        assertEquals(userAccount, settings.getString(userPrefKey, null));
    }

}
