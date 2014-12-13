package ch.epfl.scrumtool.database.google.conversion;

import java.io.IOException;

import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.exception.ScrumToolException;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;


/**
 * Exception conversion from GoogleJsonResponseException to ScrumToolException
 * @author aschneuw
 *
 */
public final class ExceptionConverter {

    public static ScrumToolException handle(IOException e) {
        if (e instanceof GoogleJsonResponseException) {
            // We wanted to make a switch/case to distinguish different exceptions thrown by the server
            // But there is a bug with this version of google app engine. We can't create our custom exceptions
            // We can only throw one type of exception from the server
            GoogleJsonError message = ((GoogleJsonResponseException) e).getDetails();
            return new NotAuthenticatedException(e, message.getMessage());
        } else {
            return new ScrumToolException(e, "Connection error");
        }
    }
}
