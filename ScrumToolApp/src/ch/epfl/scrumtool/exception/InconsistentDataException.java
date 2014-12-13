package ch.epfl.scrumtool.exception;

/**
 * 
 * @author aschneuw
 *
 */
public class InconsistentDataException extends RuntimeException  {
    private static final long serialVersionUID = -445933461414523745L;
    
    public InconsistentDataException() {
        super();
    }
    
    public InconsistentDataException(String message) {
        super(message);
    }
    
    public InconsistentDataException(Throwable t) {
        super(t);
    }
    
    public InconsistentDataException(String message, Throwable t) {
        super(message, t);
    }
}
