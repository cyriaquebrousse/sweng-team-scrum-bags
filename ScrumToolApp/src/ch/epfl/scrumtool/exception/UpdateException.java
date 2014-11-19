/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author aschneuw
 *
 */
public class UpdateException extends ScrumToolException{
    private static final long serialVersionUID = 1L;

    public UpdateException() {
        super();
    }
    
    public UpdateException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public UpdateException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}
