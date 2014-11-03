package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 * @param <A>
 */
public interface DatabaseInteraction<A> {
    /**
     * Updates the values of this object in the database
     * @param handler
     * @param successCb
     */
    void updateDatabase(DatabaseHandler<A> handler, Callback<Boolean> successCb);

    /**
     * Deletes this object from the database
     * @param handler A handler that will perform the 
     * @param successCb
     */
    void deleteFromDatabase(DatabaseHandler<A> handler, Callback<Boolean> successCb);
}
