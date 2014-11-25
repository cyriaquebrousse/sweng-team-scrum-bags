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
    
    public LoginException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public LoginException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}
