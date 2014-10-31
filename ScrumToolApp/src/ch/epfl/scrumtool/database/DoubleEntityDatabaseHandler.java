/**
 * 
 */
package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 */
public abstract class DoubleEntityDatabaseHandler<A,B> extends DatabaseHandler<A> {
    
    public abstract void insert(A first, B second );
}
