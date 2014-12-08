package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.conversion.EntityConverter;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * @author aschneuw
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public final class DSExecArgs<A, B, C> {
    private final DatastoreOperation<A, B> operation;
    private final EntityConverter<B, C> converter;
    private final Callback<C> callback;

    private DSExecArgs(DSExecArgs.Factory<A, B, C> factory) {
        Preconditions.throwIfNull("All execution arguments (operation, converter, callback) must be valid (not null)",
                factory.operation, factory.converter, factory.callback);
        
        this.operation = factory.operation;
        this.converter = factory.converter;
        this.callback = factory.callback;
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
    public static class Factory<A, B, C> {
        /**
         * 
         * @author aschneuw
         *
         */
        public static enum MODE {
            AUTHENTICATED, 
            UNAUTHETICATED
        };

        private DatastoreOperation<A, B> operation;
        private EntityConverter<B, C> converter;
        private Callback<C> callback;
        private final MODE mode;


        /**
         * 
         */
        public Factory(final MODE mode) {
            this.mode = mode;
        }

        /**
         * @return the operation
         */
        public DatastoreOperation<A, B> getOperation() {
            return operation;
        }
        /**
         * @param operation the operation to set
         */
        public DSExecArgs.Factory<A, B, C> setOperation(ScrumToolOperation<A, B> operation) {
            switch (mode) {
                case AUTHENTICATED : 
                    this.operation = new AuthenticatedOperation<A, B>(operation);
                    break;
                case UNAUTHETICATED: 
                    this.operation = new UnauthenticatedOperation<A, B>(operation);
                    break;
                default: 
                    break;
            }
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
        public DSExecArgs.Factory<A, B, C> setConverter(EntityConverter<B, C> converter) {
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
        public DSExecArgs.Factory<A, B, C> setCallback(Callback<C> callback) {
            this.callback = callback;
            return this;
        }

        public DSExecArgs<A, B, C> build() {
            return new DSExecArgs<>(this);
        }
    }
}
