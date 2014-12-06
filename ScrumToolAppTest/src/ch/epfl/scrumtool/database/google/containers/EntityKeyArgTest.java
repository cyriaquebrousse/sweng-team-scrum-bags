package ch.epfl.scrumtool.database.google.containers;

import org.junit.Test;

import junit.framework.TestCase;

public class EntityKeyArgTest extends TestCase {
    private static final String validKey = "valid";
    private static final String emptyKey = "";
    private static final Object object = new Object();
    
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
    public void testValidKeyEntity() {
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(object, validKey);
        //No Exception if valid arguments
    }

}
