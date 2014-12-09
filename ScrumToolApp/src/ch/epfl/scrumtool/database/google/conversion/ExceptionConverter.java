package ch.epfl.scrumtool.database.google.conversion;

import java.io.IOException;

import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.exception.NotFoundException;
import ch.epfl.scrumtool.exception.ScrumToolException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;


/**
 * Exception conversion from GoogleJsonResponseException to ScrumToolException
 * @author aschneuw
 *
 */
public final class ExceptionConverter {

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHROIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONBFLICT = 409;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 503;


    public static ScrumToolException handle(IOException e) {
        if (e instanceof GoogleJsonResponseException) {
            switch (((GoogleJsonResponseException) e).getStatusCode()) {
                case NOT_FOUND:
                    return new NotFoundException(e, "Not found"); 
                case UNAUTHROIZED:
                    return new NotAuthenticatedException(e, "Not authenticated!");
                default:
            }
        }
        return new ScrumToolException(e, "Server error");
    }
}
