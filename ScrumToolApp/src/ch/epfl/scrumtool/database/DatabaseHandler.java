package ch.epfl.scrumtool.database;

/**
 * 
 * Defines basic databse operations
 * 
 * @author aschneuw
 * 
 * @param <A>
 */

public interface DatabaseHandler<A> {
    /**
     * Inserts an object of type A into the database.
     * 
     * @param object
     * @param dbC
     */
    void insert(final A object, final Callback<A> cB);

    /**
     * Loads an object of type A from the database.
     * 
     * 
     * @param key
     * @param dbC
     */
    void load(final String key, final Callback<A> cB);

    /**
     * Updates an object of type A on the database.
     * 
     * @param modified
     * @param dbC
     */
    void update(final A object, final Callback<Void> cB);

    /**
     * Removes an object of type A from the database.
     * 
     * @param object
     * @param dbC
     */
    void remove(final A object, final Callback<Void> cB);
}
