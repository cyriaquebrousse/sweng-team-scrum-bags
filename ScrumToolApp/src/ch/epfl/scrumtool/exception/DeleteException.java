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
    
    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(Throwable throwable) {
        super(throwable);
    }
}
