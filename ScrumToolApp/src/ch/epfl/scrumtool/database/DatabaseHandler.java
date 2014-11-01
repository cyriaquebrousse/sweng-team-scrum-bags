package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 * @param <A> Entity type to do database operations with
 */
public abstract class DatabaseHandler<A> {
	
    public abstract void insert(A object);

    public abstract void load(String key, Callback<A> dbC);

    public abstract void loadAll(Callback<A> dbC);

    public abstract void update(A modified);

    public abstract void remove(A object);
}
