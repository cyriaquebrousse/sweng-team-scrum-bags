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
    
    public InsertException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public InsertException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
    
    
}
