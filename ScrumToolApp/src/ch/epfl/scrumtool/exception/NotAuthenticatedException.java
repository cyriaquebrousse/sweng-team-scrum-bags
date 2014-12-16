package ch.epfl.scrumtool.exception;

/**
 * Thrown when a Session is not valid anymore, or a user is not allowed to execute an
 * operation
 * @author zenhaeus
 */
public class NotAuthenticatedException extends ScrumToolException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public NotAuthenticatedException() {
        super();
    }

    /**
     * 
     * @param message
     * @param guiMessage
     */
    public NotAuthenticatedException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    /**
     * 
     * @param throwable
     * @param guiMessage
     */
    public NotAuthenticatedException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}