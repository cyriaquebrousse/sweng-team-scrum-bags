package ch.epfl.scrumtool.exception;

/**
 * 
 * @author aschneuw
 *
 */


public class NotFoundException extends ScrumToolException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param args
     */
    public NotFoundException() {
        super();
    }
    
    public NotFoundException(String message, String guiMessage) {
        super(message, guiMessage);
    }

    public NotFoundException(Throwable throwable, String guiMessage) {
        super(throwable, guiMessage);
    }

}
