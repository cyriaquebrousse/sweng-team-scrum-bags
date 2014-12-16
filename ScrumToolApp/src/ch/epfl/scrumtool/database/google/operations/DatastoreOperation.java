package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Container for a ScrumToolOperation
 * 
 * @author aschneuw
 *
 * @param <A> Operaiton input
 * @param <B> Operaiton output
 */
public abstract class DatastoreOperation<A, B> {
    private final ScrumToolOperation<A, B> operation;
    

    public DatastoreOperation(final ScrumToolOperation<A, B> operation) {
        Preconditions.throwIfNull("A datastore operation must contain valid ScrumToolOperation", operation);
        this.operation = operation;
    }
    
    /**
     * Returns the associated ScrumToolOperation
     * @return
     */
    public ScrumToolOperation<A, B> getOperation() {
        return operation;
    }
    
    /**
     * Executes the associated ScrumToolOperation
     * @param a
     * @return
     * @throws ScrumToolException
     */
    public abstract B execute(A a) throws ScrumToolException;
}