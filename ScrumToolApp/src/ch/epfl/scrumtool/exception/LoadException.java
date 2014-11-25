/**
 * 
 */
package ch.epfl.scrumtool.exception;

/**
 * @author aschneuw
 *
 */
public class LoadException extends ScrumToolException {
    private static final long serialVersionUID = 1L;

    public LoadException() {
        super();
    }
    
    public LoadException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public LoadException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }
}
