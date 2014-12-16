package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.ExceptionConverter;
import ch.epfl.scrumtool.exception.InconsistentDataException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * Abstract class to relate the corresponding operation from the AppEngine with the client
 * Furthermore, it allows us to generalize the exceptions handling)
 * 
 * @author aschneuw
 *
 * @param <A> Operaiton input
 * @param <B> Operationm output / return value
 */

public abstract class ScrumToolOperation<A, B> {
    
    public ScrumToolException handleServerException(IOException e) {
        return ExceptionConverter.handle(e);
    }
    
    /**
     * Executes the defined operation and handles it's exceptions
     * @param arg
     * @param service
     * @return
     * @throws ScrumToolException
     */
    public B execute(A arg, Scrumtool service) throws ScrumToolException {
        try {
            return operation(arg, service);
        } catch (IOException e) {
            throw handleServerException(e);
        } catch (InconsistentDataException e) {
            throw new ScrumToolException(e, "Inconsistent Server data");
        }
    }
    
    /**
     * Executes the implemented server operation
     * 
     * @param arg
     * @param service
     * @return
     * @throws IOException
     * @throws ScrumToolException
     */
    public abstract B operation(A arg, Scrumtool service) throws IOException, ScrumToolException;
}
