package ch.epfl.scrumtool.exception.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.exception.InconsistentDataException;

/**
 * 
 * @author aschneuw
 *
 */
public class InconsistentDataExceptionTest extends TestCase {

    private static final String TEST_DEBUG = "TestDebug";

    public void testInconsistentDataExceptionDebugMessageGuiMessage() {
        InconsistentDataException test = new InconsistentDataException(TEST_DEBUG);
        assertEquals(TEST_DEBUG, test.getMessage());
    }
    
    public void testInconsistentDataExceptionDefault() {
        new InconsistentDataException();
        //OK
    }

    public void testInconsistentDataExceptionThrowableStringNull() {
        NullPointerException cause = new NullPointerException();
        InconsistentDataException test = new InconsistentDataException(cause);
        assertEquals(cause, test.getCause());
    }
    
    public void testInconsistentDataExceptionThrowableString() {
        NullPointerException cause = new NullPointerException();
        InconsistentDataException test = new InconsistentDataException(TEST_DEBUG, cause);
        assertEquals(cause, test.getCause());
        assertEquals(TEST_DEBUG, test.getMessage());
    }
}