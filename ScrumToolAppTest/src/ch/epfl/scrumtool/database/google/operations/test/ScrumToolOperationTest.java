package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.operations.ScrumToolOperation;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class ScrumToolOperationTest extends TestCase {
    private static ScrumToolOperation<Object, Object> generateReturnObjectOperation() {
        return new ScrumToolOperation<Object, Object>() {
            
            @Override
            public ScrumToolException handleServerException(IOException e) {
                return new ScrumToolException(e, "Error");
            }
            
            @Override
            public Object operation(Object arg, Scrumtool service)
                    throws IOException, ScrumToolException {
                return arg;
            }
        };
    }
    
    private static ScrumToolOperation<Object, Object> generateThrowIOExceptionOperation() {
        return new ScrumToolOperation<Object, Object>() {

            @Override
            public ScrumToolException handleServerException(IOException e) {
                return new ScrumToolException(e, "Error");
            }

            @Override
            public Object operation(Object arg, Scrumtool service)
                    throws IOException, ScrumToolException {
                throw new IOException("Error");
            }
        };
        
    }
    
    private static ScrumToolOperation<Object, Object> generateThrowScrumToolExceptionOperation() {
        return new ScrumToolOperation<Object, Object>() {

            @Override
            public ScrumToolException handleServerException(IOException e) {
                return new ScrumToolException(e, "Error");
            }

            @Override
            public Object operation(Object arg, Scrumtool service)
                    throws IOException, ScrumToolException {
                throw new ScrumToolException("Error", "Error");
            }
        };
    }
    
    public void testHandleServerException() {
        ScrumToolOperation<Object, Object> op = generateThrowScrumToolExceptionOperation();
        assertTrue(op.handleServerException(new IOException()) instanceof ScrumToolException);
    }
    
    public void testExecuteScrumToolException() {
        try {
            Object test = new Object();
            ScrumToolOperation<Object, Object> op = generateThrowScrumToolExceptionOperation();
            op.execute(test, null);
            fail("ScrumToolException expected");
        } catch (ScrumToolException e) {
            //OK
        }
    }
    
    public void testExecuteIoException() {
        try {
            Object test = new Object();
            ScrumToolOperation<Object, Object> op = generateThrowScrumToolExceptionOperation();
            op.execute(test, null);
            fail("ScrumToolException expected");
        } catch (ScrumToolException e) {
            //OK
        }
    }

    public void testOperationScrumToolException() {
        Object test = new Object();
        ScrumToolOperation<Object, Object> op = generateThrowScrumToolExceptionOperation();
        try {
            op.operation(test, null);
            fail("ScrumToolException expected");
        } catch (IOException e) {
            fail("ScrumToolException expected");
        } catch (ScrumToolException e) {
            //OK
        }
    }

    
    public void testOperationIOException() {
        Object test = new Object();
        ScrumToolOperation<Object, Object> op = generateThrowIOExceptionOperation();
        
        try {
            op.operation(test, null);
            fail("IOException expected");
        } catch (IOException e) {
            // OK
        } catch (ScrumToolException e) {
            fail("IOException expected");
        }
    }
    
    public void testValidOperation() {
        Object test = new Object();
        ScrumToolOperation<Object, Object> op = generateReturnObjectOperation();
        try {
            Object result = op.operation(test, null);
            assertEquals(test, result);
        } catch (IOException e) {
            fail("Object equality expected");
        } catch (ScrumToolException e) {
            fail("Object equality expected");
        }
    }
}
