/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 * 
 * @param <A>
 * @param <B>
 */
public final class UnauthenticatedOperation<A, B> extends
        DatastoreOperation<A, B> {

    public UnauthenticatedOperation(final ScrumToolOperation<A, B> operation) {
        super(operation);
    }

    @Override
    public B execute(A a) throws ScrumToolException {
        final Scrumtool service = GoogleSession.getServiceObject();
        try {
            return getOperation().execute(a, service);
        } catch (IOException e) {
            throw new ScrumToolException(e);
        }
    }
}
