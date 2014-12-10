package ch.epfl.scrumtool.database.google.operations;

import junit.framework.TestCase;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.utils.TestConstants;

/**
 * 
 * @author aschneuw
 *
 */
public class AuthenticatedOperationTest extends TestCase {

    
    public void setup() {
        Session session = new Session(TestConstants.generateBasicUser()) {};
    }
    
    public void testNullOperation() {
        try {
            new AuthenticatedOperation<Object, Object>(null);
            fail("NullPointerException Expected for null operation argument");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testAuthenticatedOperation() {
        fail("Not yet implemented");
    }
    
    public void testExecuteNullSession() {
        fail("Not yet implemented");
    }
    
    public void testExecute() {
        fail("Not yet implemented");

    }



}
