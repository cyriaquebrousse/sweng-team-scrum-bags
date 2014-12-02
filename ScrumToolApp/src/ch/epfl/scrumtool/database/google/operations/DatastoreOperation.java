/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * 
 * @author aschneuw
 *
 * @param <A>
 * @param <B>
 */
public abstract class DatastoreOperation<A, B> {
    private final ScrumToolOperation<A, B> operation;
    

    public DatastoreOperation(final ScrumToolOperation<A, B> operation) {
        Preconditions.throwIfNull("A datastore operation must contain valid ScrumToolOperation", operation);
        this.operation = operation;
    }
    
    public ScrumToolOperation<A, B> getOperation() {
        return operation;
    }
    
    public abstract B execute(A a) throws ScrumToolException;

}