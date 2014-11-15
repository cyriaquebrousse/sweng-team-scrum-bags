/**
 * 
 */
package ch.epfl.scrumtool.database.google.conversion;

/**
 * 
 * @author Arno
 *
 * @param <A>
 * @param <B>
 */
public interface EntityConverter<A, B> {
    B convert(final A a);
}
