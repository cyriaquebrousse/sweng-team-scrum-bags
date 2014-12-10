package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.operations.ScrumToolOperation;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import junit.framework.TestCase;

/**
 * 
 * @author aschneuw
 *
 */
public class OperationExecutorTest extends TestCase {
    private static ScrumToolOperation<Object, Object> OPERATION =
            new ScrumToolOperation<Object, Object>() {
        
        @Override
        public Object operation(Object arg, Scrumtool service) throws IOException,
                ScrumToolException {
            return arg;
        }
    };
    
    private static Callback<Object> CALLBACK =
            new Callback<Object>() {
        
        @Override
        public void interactionDone(Object object) {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void failure(String errorMessage) {
            // TODO Auto-generated method stub
        }
    };
}
