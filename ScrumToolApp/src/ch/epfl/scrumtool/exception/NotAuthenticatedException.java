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
    
    public NotAuthenticatedException(String message) {
        super(message);
    }

    public NotAuthenticatedException(Throwable throwable) {
        super(throwable);
    }

    
    
}
