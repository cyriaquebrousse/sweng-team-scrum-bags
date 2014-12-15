package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * Datastore operation accessing the authenticated ServiceObject of the current GoogleSession
 * @author aschneuw
 *
 * @param <A> Operation input
 * @param <B> Operation output
 */
public final class AuthenticatedOperation<A, B> extends DatastoreOperation<A, B> {

    public AuthenticatedOperation(final ScrumToolOperation<A, B> operation) {
        super(operation);
    }
    
    @Override
    public B execute(A a) throws ScrumToolException {
        final Scrumtool service = ((GoogleSession) Session.getCurrentSession()).getAuthServiceObject();
        return getOperation().execute(a, service);
    }
}
