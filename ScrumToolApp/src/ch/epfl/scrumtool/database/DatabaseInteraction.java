package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 * @param <A>
 */
public interface DatabaseInteraction<A> {
    void updateDatabase(A reference, DatabaseHandler<A> handler);

    void deleteFromDatabase(DatabaseHandler<A> handler);
}
