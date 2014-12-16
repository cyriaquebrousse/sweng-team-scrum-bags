package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.util.Preconditions;

/**
 * This is a container class for an entity and a key
 * Can be used to define an operation where an entity of Type A needs to be inserted to a related entity with
 * the given key
 * 
 * @author aschneuw
 *
 * @param <A>
 */

public class EntityKeyArg<A> {
    private final String key;
    private final A entity;
    
    public EntityKeyArg(final A entity, final String key) {
        Preconditions.throwIfNull("EntityKeyArg arguments must not be null", entity, key);
        Preconditions.throwIfEmptyString("Key must not be an empty string", key);
        this.key = key;
        this.entity = entity;
    }

    public String getKey() {
        return key;
    }
    
    public A getEntity() {
        return entity;
    }
}
