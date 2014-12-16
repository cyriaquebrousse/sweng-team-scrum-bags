package ch.epfl.scrumtool.exception;

/**
 * Represents an exception which is thrown when the server returns
 * wrong / inconsistent / impossible data
 * @author aschneuw
 *
 */
public class InconsistentDataException extends RuntimeException  {
    private static final long serialVersionUID = -445933461414523745L;
    
    /**
     * Default constructor
     */
    public InconsistentDataException() {
        super();
    }
    
    /**
     * 
     * @param message (Debug)
     */
    public InconsistentDataException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param t
     */
    public InconsistentDataException(Throwable t) {
        super(t);
    }
    
    /**
     * 
     * @param message (Debug)
     * @param t
     */
    public InconsistentDataException(String message, Throwable t) {
        super(message, t);
    }
}
