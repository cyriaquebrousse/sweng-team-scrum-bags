package ch.epfl.scrumtool.util;

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
}
