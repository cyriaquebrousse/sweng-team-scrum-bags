package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.GoogleSession.Builder;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.utils.TestConstants;
import junit.framework.TestCase;

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
