package ch.epfl.scrumtool.exception.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.exception.ConnectionException;
/**
 * 
 * @author aschneuw
 *
 */
public class ConnectionExceptionTest extends TestCase {

    private static final String TEST_DEBUG = "TestDebug";
    private static final String TEST_GUI = "Error";

    public void testConnectionExceptionDebugMessageGuiMessage() {
        ConnectionException test = new ConnectionException(TEST_DEBUG, TEST_GUI);
        assertEquals(TEST_DEBUG, test.getMessage());
        assertEquals(TEST_GUI, test.getGUIMessage());
    }
    
    public void testConnectionExceptionDefault() {
        ConnectionException test = new ConnectionException();
        assertEquals("ScrumTool Exception", test.getGUIMessage());
    }

    public void testConnectionExceptionThrowableStringNull() {
        NullPointerException cause = new NullPointerException();
        ConnectionException test = new ConnectionException(cause, null);
        assertEquals("ScrumTool Exception", test.getGUIMessage());
        assertEquals(cause, test.getCause());
    }
    
    public void testConnectionExceptionThrowableString() {
        NullPointerException cause = new NullPointerException();
        ConnectionException test = new ConnectionException(cause, TEST_GUI);
        assertEquals(TEST_GUI, test.getGUIMessage());
        assertEquals(cause, test.getCause());
    }

}
