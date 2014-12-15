/**
 * 
 */
package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Wrapper for an entity and a corresponding KeyResponse from the server.
 * When an entity like a project is inserted to the database, we don't know the key on the client
 * at the moment of the execution of the insertion operation. Therefore, the server sends a keyResponse
 * with the generated key for the inserted entity. In a later state. the entity can be updated with the
 * key
 * 
 * @author aschneuw
 *
 * @param <A>
 */
public final class InsertResponse<A> {
    private final A entity;
    private final KeyResponse keyResponse;
    
    public InsertResponse(final A entity, final KeyResponse keyResponse) {
        Preconditions.throwIfNull("Must contain a valid entity and keyResponse", entity, keyResponse);
        Preconditions.throwIfEmptyString("The key must no be empty", keyResponse.getKey());
        this.entity = entity;
        this.keyResponse = keyResponse;
    }

    /**
     * @return the entity
     */
    public A getEntity() {
        return entity;
    }

    /**
     * @return the keyReponse
     */
    public KeyResponse getKeyReponse() {
        return keyResponse;
    }
}
