package ch.epfl.scrumtool.database.google.containers;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.utils.TestConstants;

public class InsertResponseTest extends TestCase {

    public void testInsertResponseNullEntity() {
        try {
            @SuppressWarnings("unused")
            final InsertResponse<Object> insResp = 
                    new InsertResponse<Object>(null, TestConstants.VALID_KEY_RESPONSE);
            fail("InsertResponse must contain a valid entity object");
        } catch (NullPointerException e){
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testInsertResponseNullKey() {
        try {
            @SuppressWarnings("unused")
            final InsertResponse<Object> insResp = 
                    new InsertResponse<Object>(new Object(), null);
            fail("InsertResponse must contain a KeyReponse object");
        } catch (NullPointerException e){
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testInsertResponseInvalidKey() {
        try {
            @SuppressWarnings("unused")
            final InsertResponse<Object> insResp = 
                    new InsertResponse<Object>(new Object(), TestConstants.INVALID_KEY_RESPONSE);
            fail("InsertResponse must contain a valid KeyReponse object");
        } catch (IllegalArgumentException e){
            
        } catch (Exception e) {
            fail("IllegalArgumentException expected");
        }
    }
    
    public void testInsertResponse() {
        @SuppressWarnings("unused")
        final InsertResponse<Object> insResp = new InsertResponse<Object>(new Object(), TestConstants.VALID_KEY_RESPONSE);
        //Pass if no exception
    }

    public void testGetEntity() {
        Object test = new Object();
        final InsertResponse<Object> insResp = new InsertResponse<Object>(test, TestConstants.VALID_KEY_RESPONSE);
        assertEquals(insResp.getEntity(), test);
    }

    public void testGetkeyReponse() {
        final InsertResponse<Object> insResp = new InsertResponse<Object>(new Object(), TestConstants.VALID_KEY_RESPONSE);
        assertEquals(insResp.getKeyReponse(), TestConstants.VALID_KEY_RESPONSE);
    }

}
