/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author aschneuw
 *
 */
public class LoginException extends ScrumToolException{
    private static final long serialVersionUID = 1L;
    
    public LoginException() {
        super();
    }
    
    public LoginException(String message) {
        super(message);
    }

    public LoginException(Throwable throwable) {
        super(throwable);
    }
}
