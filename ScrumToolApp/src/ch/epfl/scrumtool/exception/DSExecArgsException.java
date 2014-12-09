package ch.epfl.scrumtool.exception;

/**
 * @author Arno
 */
public class DSExecArgsException extends NullPointerException {
    private static final long serialVersionUID = 1L;
    
    public DSExecArgsException() {
        super();
    }
    
    public DSExecArgsException(String message) {
        super(message);
    }
}
