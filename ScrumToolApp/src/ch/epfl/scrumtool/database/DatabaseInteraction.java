/**
 * 
 */
package ch.epfl.scrumtool.database;

/**
 * @author Arno
 * 
 */
public interface DatabaseInteraction<A> {
    public void updateDatabase(A reference, DatabaseHandler<A> handler);

    public void deleteFromDatabase(DatabaseHandler<A> handler);
}
