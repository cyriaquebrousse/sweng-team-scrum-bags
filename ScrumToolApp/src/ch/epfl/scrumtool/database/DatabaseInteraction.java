package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 * @param <A>
 */
public interface DatabaseInteraction<A> {
    /**
     * @param handler
     * @param successCb
     */
    void updateDatabase(DatabaseHandler<A> handler, Callback<Boolean> successCb);

    /**
     * @param handler
     * @param successCb
     */
    void deleteFromDatabase(DatabaseHandler<A> handler, Callback<Boolean> successCb);
}
