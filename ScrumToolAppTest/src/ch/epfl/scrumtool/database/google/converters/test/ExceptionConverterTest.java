package ch.epfl.scrumtool.database.google.converters.test;

import java.io.IOException;

import junit.framework.TestCase;

import ch.epfl.scrumtool.database.google.conversion.ExceptionConverter;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.exception.ScrumToolException;

import com.google.api.client.googleapis.testing.json.GoogleJsonResponseExceptionFactoryTesting;
import com.google.api.client.json.gson.GsonFactory;

/**
 * 
 * @author aschneuw
 *
 */
public class ExceptionConverterTest extends TestCase {

    public void testNotAuthenticatedException() {
        try {
            GoogleJsonResponseExceptionFactoryTesting.newMock(new GsonFactory(), 401, "Test");
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof NotAuthenticatedException);
            assertTrue(conv.getCause().equals(e));
        }
    }
}
