/**
 * 
 */
package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * @author aschneuw
 *
 * @param <A>
 */
public class InsertResponse<A> {
    private final A entity;
    private final KeyResponse keyResponse;
    
    /**
     * 
     */
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
    public KeyResponse getkeyReponse() {
        return keyResponse;
    }
}
