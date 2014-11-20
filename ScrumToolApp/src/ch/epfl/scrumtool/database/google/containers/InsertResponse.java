/**
 * 
 */
package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;

/**
 * @author aschneuw
 *
 * @param <A>
 */
public class InsertResponse<A> {
    private final A entity;
    private final OperationStatus opStat;
    
    /**
     * 
     */
    public InsertResponse(final A entity, final OperationStatus opStat) {
        if (entity == null) {
            throw new NullPointerException("Entity can't be null");
        }
        
        if (opStat == null) {
            throw new NullPointerException("OperationStatus needed");
        }
        
        if (opStat.getKey() == null) {
            throw new NullPointerException("OperationStatus needs a key");
        }
        this.entity = entity;
        this.opStat = opStat;
    }

    /**
     * @return the entity
     */
    public A getEntity() {
        return entity;
    }

    /**
     * @return the opStat
     */
    public OperationStatus getOpStat() {
        return opStat;
    }
}
