package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.ExceptionConverter;
import ch.epfl.scrumtool.exception.InconsistentDataException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * @author aschneuw
 *
 * @param <A>
 * @param <B>
 */

public abstract class ScrumToolOperation<A, B> {
    
    public ScrumToolException handleServerException(IOException e) {
        return ExceptionConverter.handle(e);
    }
    
    public B execute(A arg, Scrumtool service) throws ScrumToolException {
        try {
            return operation(arg, service);
        } catch (IOException e) {
            throw handleServerException(e);
        } catch (InconsistentDataException e) {
            throw new ScrumToolException(e, "Inconsistent Server data");
        }
    }
    
    public abstract B operation(A arg, Scrumtool service) throws IOException, ScrumToolException;
}
