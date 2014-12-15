/**
 * 
 */
package ch.epfl.scrumtool.database.google.conversion;

/**
 * Ensures conversion and data integrity between application entities and database entities
 * 
 * @author Arno
 * 
 * @param <A>
 * @param <B>
 */
public interface EntityConverter<A, B> {
    B convert(final A a);
}
