package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Unauthenticated DatastoreOperation. Can be used with a custom Scrumtool object which is not necxessarily related
 * to the GoogleSession
 * @author aschneuw
 * 
 * @param <A>
 * @param <B>
 */
public final class UnauthenticatedOperation<A, B> extends
        DatastoreOperation<A, B> {
    private final Scrumtool service;

    public UnauthenticatedOperation(final ScrumToolOperation<A, B> operation, Scrumtool service) {
        super(operation);
        Preconditions.throwIfNull("Must have a valid Scrumtool service object", service);
        this.service = service;
    }

    /**
     * executes the operation with with the objects service object
     */
    @Override
    public B execute(A a) throws ScrumToolException {
        return getOperation().execute(a, service);
    }
}
