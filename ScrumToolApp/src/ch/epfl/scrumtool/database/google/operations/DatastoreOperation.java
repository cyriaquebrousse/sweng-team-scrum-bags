/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

/**
 * 
 * @author Arno
 *
 * @param <A>
 * @param <B>
 */
public interface DatastoreOperation<A, B> {
    B execute(final A a) throws IOException;
}
