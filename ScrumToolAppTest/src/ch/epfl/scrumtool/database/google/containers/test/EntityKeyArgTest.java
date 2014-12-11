package ch.epfl.scrumtool.database.google.containers.test;

import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import junit.framework.TestCase;
/**
 * 
 * @author aschneuw
 *
 */
public class EntityKeyArgTest extends TestCase {
    private static final String validKey = "valid";
    private static final String emptyKey = "";
    private static final Object object = new Object();
    
    
    public void testNullKey() {
        try {
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(object, null);
        fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e){
            fail("A null key should throw a NullPointerException");
        }
    }
    
    public void testEmptyKey() {
        try{
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(object, emptyKey);
        fail("IllegalArgument expected");
        } catch (IllegalArgumentException e) {
            
        } catch (Exception e){
            fail("An empty key should throw a IllegalArgumentException");
        }
    }
    
    public void testNullEntity() {
        try {
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(null, validKey);
        fail("NullPointerException expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e){
            fail("A null entity should throw a NullPointerException");
        }
    }
    
    public void testValidKeyEntity() {
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(object, validKey);
        //No Exception if valid arguments
    }

}
