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

    /**
     * Throws {@link AssertionError} if the condition doesn't hold
     * 
     * @param condition
     *            condition to assess
     */
    public static void assertTrue(boolean condition) {
        if (!condition && ENABLE_ASSERTIONS) {
            throw new AssertionError();
        }
    }

    /**
     * Throws {@link AssertionError} with the provided message if the condition
     * doesn't hold
     * 
     * @param condition
     *            condition to assess
     * @param message
     *            message to fail with
     */
    public static void assertTrue(boolean condition, String message) {
        if (!condition && ENABLE_ASSERTIONS) {
            throw new AssertionError(message);
        }
    }
}
