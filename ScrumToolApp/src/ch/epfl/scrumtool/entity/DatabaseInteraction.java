/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Arno
 *
 */
public interface DatabaseInteraction<A> {
	public void updateDatabase(A reference, DatabaseHandler<A> handler);
	public void deleteFromDatabase(DatabaseHandler<A> handler);
}
