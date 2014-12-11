package ch.epfl.scrumtool.database.google.containers.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
/**
 * 
 * @author aschneuw
 *
 */
public class InsertResponseTest extends TestCase {

    public void testInsertResponseNullEntity() {
        try {
            new InsertResponse<Object>(null, ServerClientEntities.VALID_KEY_RESPONSE);
            fail("InsertResponse must contain a valid entity object");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testInsertResponseNullKey() {
        try {
            new InsertResponse<Object>(new Object(), null);
            fail("InsertResponse must contain a KeyReponse object");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testInsertResponseInvalidKey() {
        try {
            new InsertResponse<Object>(new Object(), ServerClientEntities.INVALID_KEY_RESPONSE);
            fail("InsertResponse must contain a valid KeyReponse object");
        } catch (IllegalArgumentException e) {
            
        }
    }
    
    public void testInsertResponse() {
        new InsertResponse<Object>(new Object(), ServerClientEntities.VALID_KEY_RESPONSE);
        //Pass if no exception
    }

    public void testGetEntity() {
        Object test = new Object();
        final InsertResponse<Object> insResp =
                new InsertResponse<Object>(test, ServerClientEntities.VALID_KEY_RESPONSE);
        assertEquals(insResp.getEntity(), test);
    }

    public void testGetkeyReponse() {
        final InsertResponse<Object> insResp =
                new InsertResponse<Object>(new Object(), ServerClientEntities.VALID_KEY_RESPONSE);
        assertEquals(insResp.getKeyReponse(), ServerClientEntities.VALID_KEY_RESPONSE);
    }

}
