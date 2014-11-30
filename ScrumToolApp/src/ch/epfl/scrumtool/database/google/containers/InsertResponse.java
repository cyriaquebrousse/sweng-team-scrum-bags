/**
 * 
 */
package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;

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
        if (entity == null) {
            throw new NullPointerException("Entity can't be null");
        }
        
        if (keyResponse == null) {
            throw new NullPointerException("KeyReponse needed");
        }
        
        if (keyResponse.getKey() == null) {
            throw new NullPointerException("KeyResponse needs a key");
        }
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
