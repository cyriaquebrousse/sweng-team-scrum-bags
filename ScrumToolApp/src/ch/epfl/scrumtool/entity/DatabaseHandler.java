/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Arno
 * 
 */
public abstract class DatabaseHandler<A> {

    public abstract void insert(A object);

    public abstract void load(String key, DatabaseCallback<A> dbC);

    public abstract void loadAll(DatabaseCallback<A> dbC);

    public abstract void update(A modified);

    public abstract void remove(A object);
}
