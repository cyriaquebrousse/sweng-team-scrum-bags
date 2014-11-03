package ch.epfl.scrumtool.database;

import java.util.List;

/**
 * @author aschneuw
 *
 * @param <A> Entity type of object that gets sent to the datastore
 */
public abstract class DatabaseHandler<A> {
	
    /**
     * Inserts an object of type A into the database
     * @param object
     * @param dbC
     */
    public abstract void insert(A object, Callback<Boolean> dbC);

    /**
     * loads an object of type A from the database
     * @param key
     * @param dbC
     */
    public abstract void load(String key, Callback<A> dbC);

    /**
     * Loads all objects of type A from the database
     * @param filter
     * @param dbC
     */
    public abstract void loadAll(Callback<List<A>> dbC);

    /**
     * Updates an object of type A on the database
     * @param modified
     * @param dbC
     */
    public abstract void update(A modified, Callback<Boolean> dbC);

    /**
     * Removes an object of type A from the database
     * @param object
     * @param dbC
     */
    public abstract void remove(A object, Callback<Boolean> dbC);
}
