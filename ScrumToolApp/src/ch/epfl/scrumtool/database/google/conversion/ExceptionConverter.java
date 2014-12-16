package ch.epfl.scrumtool.database.google.conversion;

import java.io.IOException;

import ch.epfl.scrumtool.exception.ConnectionException;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.exception.ScrumToolException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;


/**
 * Exception conversion from GoogleJsonResponseException to ScrumToolException
 * @author aschneuw
 *
 */
public final class ExceptionConverter {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOTFOUND = 404;
    private static final int CONFLICT = 409;
    
    /**
     * handels an IOException based on Google AppEngine server errors
     * @param e
     * @return
     */
    public static ScrumToolException handle(IOException e) {
        if (e instanceof GoogleJsonResponseException) {
            //Due to a bug in the current version of AppEngine we can't create custom exceptions on the server side
            //with our own error codes.
            GoogleJsonResponseException exception = (GoogleJsonResponseException) e;
            switch (exception.getStatusCode()){
                case FORBIDDEN:
                    return new NotAuthenticatedException(e, "Authentication / login error");
                case CONFLICT:
                case NOTFOUND:
                case UNAUTHORIZED:
                    return new ScrumToolException(e, exception.getDetails().getMessage());
                default:
                    return new ScrumToolException(e, "Server Error");
            }
        } else {
            //If there is not GoogleJsonResponseException means that the problem is not on the database.
            return new ConnectionException(e, "Connection error");
        }
    }
}