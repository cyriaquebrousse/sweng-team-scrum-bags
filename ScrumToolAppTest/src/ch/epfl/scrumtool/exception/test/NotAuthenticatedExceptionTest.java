package ch.epfl.scrumtool.exception.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;

/**
 * 
 * @author aschneuw
 *
 */
public class NotAuthenticatedExceptionTest extends TestCase {
    private static final String TEST_DEBUG = "TestDebug";
    private static final String TEST_GUI = "Error";

    public void testNotAuthenticatedExceptionDebugMessageGuiMessage() {
        NotAuthenticatedException test = new NotAuthenticatedException(TEST_DEBUG, TEST_GUI);
        assertEquals(TEST_DEBUG, test.getMessage());
        assertEquals(TEST_GUI, test.getGUIMessage());
    }
    
    public void testNotAuthenticatedExceptionDefault() {
        NotAuthenticatedException test = new NotAuthenticatedException();
        assertEquals("ScrumTool Exception", test.getGUIMessage());
    }

    public void testNotAuthenticatedExceptionThrowableStringNull() {
        NullPointerException cause = new NullPointerException();
        NotAuthenticatedException test = new NotAuthenticatedException(cause, null);
        assertEquals("ScrumTool Exception", test.getGUIMessage());
        assertEquals(cause, test.getCause());
    }
    
    public void testNotAuthenticatedExceptionThrowableString() {
        NullPointerException cause = new NullPointerException();
        NotAuthenticatedException test = new NotAuthenticatedException(cause, TEST_GUI);
        assertEquals(TEST_GUI, test.getGUIMessage());
    }
}
