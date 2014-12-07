package ch.epfl.scrumtool.util;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;

/**
 * @author Cyriaque Brousse
 */
public final class Preconditions {
    
    /**
     * Throws a {@link NullPointerException} with {@code message} if any of the
     * provided {@code objects} is null
     * 
     * @param message
     *            message to throw
     * @param objects
     *            objects to check
     */
    public static void throwIfNull(String message, Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new NullPointerException(message);
            }
        }
    }
    
    public static void throwIfEmptyString(String message, String string) {
        throwIfNull("Nothing to check", string);
        if (string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void throwIfInvalidKey(String key) {
        throwIfEmptyString("Key must not be empty", key);
    }
    
    public static void throwIfInvalidEmail(String email) {
        assertTrue(email != null);
        if (!InputVerifiers.emailIsValid(email)) {
            throw new IllegalArgumentException("E-Mail address is invalid");
        }
    }
}
