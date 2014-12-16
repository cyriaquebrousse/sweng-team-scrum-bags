package ch.epfl.scrumtool.exception.test;

import ch.epfl.scrumtool.exception.ScrumToolException;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class ScrumToolExceptionTest extends TestCase {
    private static final String TEST_DEBUG = "TestDebug";
    private static final String TEST_GUI = "Error";

    public void testScrumToolExceptionDebugMessageGuiMessage() {
        ScrumToolException test = new ScrumToolException(TEST_DEBUG, TEST_GUI);
        assertEquals(TEST_DEBUG, test.getMessage());
        assertEquals(TEST_GUI, test.getGUIMessage());
    }
    
    public void testScrumToolExceptionDefault() {
        ScrumToolException test = new ScrumToolException();
        assertEquals("ScrumTool Exception", test.getGUIMessage());
    }

    public void testScrumToolExceptionThrowableStringNull() {
        NullPointerException cause = new NullPointerException();
        ScrumToolException test = new ScrumToolException(cause, null);
        assertEquals("ScrumTool Exception", test.getGUIMessage());
        assertEquals(cause, test.getCause());
    }
    
    public void testScrumToolExceptionThrowableString() {
        NullPointerException cause = new NullPointerException();
        ScrumToolException test = new ScrumToolException(cause, TEST_GUI);
        assertEquals(TEST_GUI, test.getGUIMessage());
        assertEquals(cause, test.getCause());
    }
}
