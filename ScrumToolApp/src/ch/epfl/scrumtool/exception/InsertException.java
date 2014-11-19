/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author aschneuw
 *
 */
public class InsertException extends ScrumToolException{
    private static final long serialVersionUID = 1L;
    
    
    public InsertException() {
        super();
    }
    
    public InsertException(String message) {
        super(message);
    }

    public InsertException(Throwable throwable) {
        super(throwable);
    }
    
    
}
