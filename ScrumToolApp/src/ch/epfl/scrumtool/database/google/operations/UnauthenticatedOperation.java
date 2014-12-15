package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Unauthenticated DatastoreOperation. 
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

    @Override
    public B execute(A a) throws ScrumToolException {
        return getOperation().execute(a, service);
    }
}
