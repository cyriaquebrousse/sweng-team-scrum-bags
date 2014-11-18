package ch.epfl.scrumtool.exception;

/**
 * 
 * @author aschneuw, zenhaeus
 *
 */
public class ScrumToolException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ScrumToolException() {
        super();
    }
    
    public ScrumToolException(String message) {
        super(message);
    }

    public ScrumToolException(Throwable throwable) {
        super(throwable);
    }
}
