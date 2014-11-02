package ch.epfl.scrumtool.database;

/**
 * This class provides functions to do database operations which require 
 * two entities.
 * @author aschneuw
 *
 * @param <A> first entity type to include in database operation
 * @param <B> second entity type to include in database operation
 */
public abstract class DoubleEntityDatabaseHandler<A, B> extends DatabaseHandler<A> {
    
    public abstract void insert(A first, B second);
}
