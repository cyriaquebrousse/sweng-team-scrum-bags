package ch.epfl.scrumtool.util;

import static ch.epfl.scrumtool.database.google.AppEngineUtils.ENABLE_ASSERTIONS;

/**
 * Used to mock the Java asserts which are ignored in Android<br>
 * Enable or disable assertions in
 * {@link ch.epfl.scrumtool.database.google.AppEngineUtils#ENABLE_ASSERTIONS}.
 * 
 * @author Cyriaque Brousse
 */
public final class Assertions {

    public static void assertTrue(boolean condition) {
        if (!condition && ENABLE_ASSERTIONS) {
            throw new AssertionError();
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition && ENABLE_ASSERTIONS) {
            throw new AssertionError(message);
        }
    }
}
