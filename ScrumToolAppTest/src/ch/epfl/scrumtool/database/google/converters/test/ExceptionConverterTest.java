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
            assertEquals(conv.getGUIMessage(), "Authentication / login error");
        }
    }
    
    public void testForbidden() {
        String error = "Test";
        try {
            
            GoogleJsonResponseExceptionFactoryTesting.newMock(new GsonFactory(), 403, error);
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof ScrumToolException);
            assertTrue(conv.getCause().equals(e));
            assertTrue(conv.getGUIMessage().equals(error));
        }
    }
    
    public void testNotFound() {
        String error = "Test";
        try {
            
            GoogleJsonResponseExceptionFactoryTesting.newMock(new GsonFactory(), 404, error);
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof ScrumToolException);
            assertTrue(conv.getCause().equals(e));
            assertTrue(conv.getGUIMessage().equals(error));
        }
    }
    
    public void testConflict() {
        String error = "Test";
        try {
            
            GoogleJsonResponseExceptionFactoryTesting.newMock(new GsonFactory(), 409, error);
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof ScrumToolException);
            assertTrue(conv.getCause().equals(e));
            assertTrue(conv.getGUIMessage().equals(error));
        }
    }
    
    public void testOther() {
        String error = "Test";
        try {
            
            GoogleJsonResponseExceptionFactoryTesting.newMock(new GsonFactory(), 410, error);
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof ScrumToolException);
            assertTrue(conv.getCause().equals(e));
            assertTrue(conv.getGUIMessage().equals("Server error"));
        }
    }
    
    public void testConnection() {
        try {
            throw new IOException("Error");
        } catch (IOException e) {
            ScrumToolException conv = ExceptionConverter.handle(e);
            assertTrue(conv instanceof ScrumToolException);
            assertTrue(conv.getCause().equals(e));
            assertEquals(conv.getGUIMessage(), "Connection error");
        }
    }
}
