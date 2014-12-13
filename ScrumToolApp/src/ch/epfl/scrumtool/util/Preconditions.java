package ch.epfl.scrumtool.util;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import ch.epfl.scrumtool.exception.InconsistentDataException;

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
        int index = 0;
        for (Object o : objects) {
            if (o == null) {
                throw new NullPointerException(message + "; Argument n°: " + index);
            }
            index++;
        }
    }
    
    public static void throwIfInconsistentData(String message, Object... objects) {
        try {
            throwIfNull(message, objects);
        } catch (NullPointerException e) {
            throw new InconsistentDataException(message, e);
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
            throw new InconsistentDataException(
                    new IllegalArgumentException("E-Mail address is invalid")
            );
        }
    }
}
