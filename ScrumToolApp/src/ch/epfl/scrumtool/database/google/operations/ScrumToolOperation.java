/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 * @param <A>
 * @param <B>
 */

public interface ScrumToolOperation<A, B> {
    B execute(A arg, Scrumtool service) throws IOException;
}
