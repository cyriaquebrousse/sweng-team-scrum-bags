package ch.epfl.scrumtool.exception;

/**
 * @author zenhaeus
 *
 */
public class NotAuthenticatedException extends ScrumToolException {
    private static final long serialVersionUID = 1L;
    
    public NotAuthenticatedException() {
        super();
    }
    
    public NotAuthenticatedException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public NotAuthenticatedException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }

    
    
}
