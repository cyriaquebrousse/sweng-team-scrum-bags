/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * @author Arno
 *
 */
public abstract class DatabaseHandler<A> {
	public abstract void insert(A object);
	public abstract A get(Key key);
	public abstract List<A> getAll();
	public abstract void update(A modified);
	public abstract void remove(A object);
}
