/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author aschneuw
 *
 */
public class DeleteException extends ScrumToolException {
    private static final long serialVersionUID = 1L;

    public DeleteException() {
        super();
    }
    
    public DeleteException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public DeleteException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}
