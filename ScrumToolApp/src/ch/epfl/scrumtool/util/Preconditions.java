package ch.epfl.scrumtool.util;

import ch.epfl.scrumtool.util.gui.InputVerifiers;

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
        assert string != null;
        if (string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void throwIfInvalidEmail(String email) {
        assert email != null;
        if (!InputVerifiers.emailIsValid(email)) {
            throw new IllegalArgumentException("E-Mail address is invalid");
        }
    }
    
}
