/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.conversion.EntityConverter;

/**
 * 
 * @author Arno
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public final class DSExecArgs<A, B, C> {
    private final DatastoreOperation<A, B> operation;
    private final EntityConverter<B, C> converter;
    private final Callback<C> callback;
    
    private DSExecArgs(DSExecArgs.Builder<A, B, C> builder) {
        this.operation = builder.operation;
        this.converter = builder.converter;
        this.callback = builder.callback;
    }
    
    /**
     * @return the operation
     */
    public DatastoreOperation<A, B> getOperation() {
        return operation;
    }

    /**
     * @return the converter
     */
    public EntityConverter<B, C> getConverter() {
        return converter;
    }

    /**
     * @return the callback
     */
    public Callback<C> getCallback() {
        return callback;
    }

    /**
     * 
     * @author Arno
     *
     * @param <A>
     * @param <B>
     * @param <C>
     */
    public static class Builder<A, B, C> {
        private DatastoreOperation<A, B> operation;
        private EntityConverter<B, C> converter;
        private Callback<C> callback;
        
        /**
         * @return the operation
         */
        public DatastoreOperation<A, B> getOperation() {
            return operation;
        }
        /**
         * @param operation the operation to set
         */
        public DSExecArgs.Builder<A, B, C> setOperation(DatastoreOperation<A, B> operation) {
            this.operation = operation;
            return this;
        }
        /**
         * @return the converter
         */
        public EntityConverter<B, C> getConverter() {
            return converter;
        }
        /**
         * @param converter the converter to set
         */
        public DSExecArgs.Builder<A, B, C> setConverter(EntityConverter<B, C> converter) {
            this.converter = converter;
            return this;
        }
        /**
         * @return the callback
         */
        public Callback<C> getCallback() {
            return callback;
        }
        /**
         * @param callback the callback to set
         */
        public DSExecArgs.Builder<A, B, C> setCallback(Callback<C> callback) {
            this.callback = callback;
            return this;
        }
        
        public DSExecArgs<A, B, C> build() {
            if (converter != null && callback != null && operation != null) {
                return new DSExecArgs<>(this);
            } else {
                return null;
            }
            
            //TODO ExceptionHandling
        }
    }
}
