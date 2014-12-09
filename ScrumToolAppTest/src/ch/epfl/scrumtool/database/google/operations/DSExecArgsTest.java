package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.conversion.EntityConverter;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class DSExecArgsTest extends TestCase {
    private static final ScrumToolOperation<Object, Object> OPERATION =
            new ScrumToolOperation<Object, Object>() {
        
        @Override
        public Object operation(Object arg, Scrumtool service) throws IOException,
                ScrumToolException {
            return arg;
        }
    };
    
    private static final Callback<Object> CALLBACK = new Callback<Object>() {
        
        @Override
        public void interactionDone(Object object) {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void failure(String errorMessage) {
            // TODO Auto-generated method stub
        }
    };
    
    private static final EntityConverter<Object, Object> CONVERTER = 
            new EntityConverter<Object, Object>() {
        
        @Override
        public Object convert(Object a) {
            return a;
        }
    };
    
    public void testFactoryNullAuthMode() {
        try {
            new Factory<Object, Object, Object>(null); 
            fail("NullPointerExceptionExpected for invalid auth mode");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testFactoryGetSetCallback() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setCallback(CALLBACK);
        assertEquals(CALLBACK, factory.getCallback());
    }
    
    public void testFactoryGetSetConverter() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        assertEquals(CONVERTER, factory.getConverter());
    }
    
    public void testFactoryGetSetOperation() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setOperation(OPERATION);
        assertEquals(OPERATION, factory.getOperation().getOperation());
    }
    
    public void testNullCallback() {
        try {
            DSExecArgs.Factory<Object, Object, Object> factory =
                    new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
            factory.setConverter(CONVERTER);
            factory.setOperation(OPERATION);
            factory.build();
            fail("NullPointerException Expected for incomplete DSExecArgs");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullOperation() {
        try {
            DSExecArgs.Factory<Object, Object, Object> factory =
                    new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
            factory.setConverter(CONVERTER);
            factory.setCallback(CALLBACK);
            factory.build();
            fail("NullPointerException Expected for incomplete DSExecArgs");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullConverter() {
        try {
            DSExecArgs.Factory<Object, Object, Object> factory =
                    new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
            factory.setCallback(CALLBACK);
            factory.setOperation(OPERATION);
            factory.build();
            fail("NullPointerException Expected for incomplete DSExecArgs");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testValidArgs() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        factory.build();
    }
   
    public void testGetOperation() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        DSExecArgs<Object, Object, Object> args = factory.build();
        assertEquals(CONVERTER, args.getConverter());
    }

    public void testGetConverter() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        DSExecArgs<Object, Object, Object> args = factory.build();
        assertEquals(OPERATION, args.getOperation().getOperation());
    }

    public void testGetCallback() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        DSExecArgs<Object, Object, Object> args = factory.build();
        assertEquals(CALLBACK, args.getCallback());
    }
    
    public void testAuthenticatedOperation() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.AUTHENTICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        DSExecArgs<Object, Object, Object> args = factory.build();
        assertTrue(args.getOperation() instanceof AuthenticatedOperation<?, ?>);
    }
    
    public void testUnAuthenticatedOperation() {
        DSExecArgs.Factory<Object, Object, Object> factory =
                new Factory<Object, Object, Object>(MODE.UNAUTHETICATED); 
        factory.setConverter(CONVERTER);
        factory.setCallback(CALLBACK);
        factory.setOperation(OPERATION);
        DSExecArgs<Object, Object, Object> args = factory.build();
        assertTrue(args.getOperation() instanceof UnauthenticatedOperation<?, ?>);
    }
}
