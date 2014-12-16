package ch.epfl.scrumtool.exception;

/**
 * This exception is thrown when we get an exception when trying to reach the server, but that
 * exception is not a server Exception
 * 
 * @author vincent
 *
 */
public class ConnectionException extends ScrumToolException {
    private static final long serialVersionUID = 1L;
    
    /**
     * default constructor
     */
    public ConnectionException() {
        super();
    }

    /**
     * 
     * @param message (Debug)
     * @param guiMessage (GUI Error message)
     */
    public ConnectionException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    /**
     * 
     * @param throwable
     * @param guiMessage
     */
    public ConnectionException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}