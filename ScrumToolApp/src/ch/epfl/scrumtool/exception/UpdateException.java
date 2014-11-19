/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author Arno
 *
 */
public class UpdateException extends ScrumToolException{
    private static final long serialVersionUID = 1L;

    public UpdateException() {
        super();
    }
    
    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(Throwable throwable) {
        super(throwable);
    }
}
