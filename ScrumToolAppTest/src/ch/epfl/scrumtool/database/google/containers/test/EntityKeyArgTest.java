package ch.epfl.scrumtool.database.google.containers.test;

import ch.epfl.scrumtool.database.google.containers.EntityKeyArg;
import junit.framework.TestCase;
/**
 * 
 * @author aschneuw
 *
 */
public class EntityKeyArgTest extends TestCase {
    private static final String VALID_KEY = "valid";
    private static final String INVALID_KEY = "";
    private static final Object OBJECT = new Object();


    public void testNullKey() {
        try {
            @SuppressWarnings("unused")
            EntityKeyArg<Object> args = new EntityKeyArg<Object>(OBJECT, null);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {
        }
    }

    public void testEmptyKey() {
        try {
            @SuppressWarnings("unused")
            EntityKeyArg<Object> args = new EntityKeyArg<Object>(OBJECT, INVALID_KEY);
            fail("IllegalArgument expected");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testNullEntity() {
        try {
            @SuppressWarnings("unused")
            EntityKeyArg<Object> args = new EntityKeyArg<Object>(null, VALID_KEY);
            fail("NullPointerException expected");
        } catch (NullPointerException e) {

        }
    }

    public void testValidKeyEntity() {
        @SuppressWarnings("unused")
        EntityKeyArg<Object> args = new EntityKeyArg<Object>(OBJECT, VALID_KEY);
        //No Exception if valid arguments
    }

}
