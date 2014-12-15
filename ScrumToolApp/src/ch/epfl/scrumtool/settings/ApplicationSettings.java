package ch.epfl.scrumtool.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class provides methods to save ScrumToolApp's settings persistently.
 * @author zenhaeus
 */
public class ApplicationSettings {
    private static final String PREFERENCES = "PrefsFile";
    private static final String USER_PREF_KEY = "userPref";

    /**
     * 
     * @param context
     * @return String
     * Returns the cached user id
     */
    public static String getCachedUser(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, 0);
        return settings.getString(USER_PREF_KEY, null);
    }
    
    /**
     * Removes the currently cached user from settings
     * @param context
     */
    public static void removeCachedUser(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(USER_PREF_KEY);
        editor.commit();
    }
    
    /**
     * Save user to settings
     * @param accName
     */
    public static void saveCachedUser(Context context, String accName) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_PREF_KEY, accName);
        editor.commit();
    }
}