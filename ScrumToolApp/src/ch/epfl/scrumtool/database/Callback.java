package ch.epfl.scrumtool.database;

/**
 * @author zenhaeus
 * 
 * @param <A>
 *            type of object that will be handed to callback function
 */
public interface Callback<A> {

    /**
     * Function to call when the database operation is finished
     * 
     * @param object
     */
    void interactionDone(A object);
    
    
    /**
     * Function to call when the database operation failed
     * @param errorMessage
     */
    void failure(final String errorMessage);
    
    
}
