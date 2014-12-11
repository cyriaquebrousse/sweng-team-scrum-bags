package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.conversion.EntityConverter;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.ScrumToolOperation;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class OperationExecutorTest extends TestCase {
    private static final ScrumToolOperation<Object, Object> OPERATION =
            new ScrumToolOperation<Object, Object>() {
        @Override
        public Object operation(Object arg, Scrumtool service) throws IOException,
                ScrumToolException {
            return arg;
        }
    };
    
    private static final ScrumToolOperation<Object, Object> OPERATIONEXCEPTION =
            new ScrumToolOperation<Object, Object>() {
        @Override
        public Object operation(Object arg, Scrumtool service) throws IOException,
                ScrumToolException {
            throw new ScrumToolException();
        }
    };
    
    private static final EntityConverter<Object, Object> CONVERTER = new EntityConverter<Object, Object>() {
        
        @Override
        public Object convert(Object a) {
            return a;
        }
    };
    
    /**
     * Callback for asynchronous testing
     * @author aschneuw
     *
     */
    private static abstract class TestCallback implements Callback<Object> {
        private boolean success = false;
        private String failureReason = "";
        public boolean isSuccess() {
            return success;
        }
        public void setSuccess(boolean success) {
            this.success = success;
        }
        public String getFailureReason() {
            return failureReason;
        }
        public void setFailureReason(String failureReason) {
            this.failureReason = failureReason;
        }
    }
    
    public void testExecuteInteractionDone() {
        //http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
        final CountDownLatch signal = new CountDownLatch(1);
        DSExecArgs.Factory<Object, Object, Object> factory = new Factory<Object, Object, Object>(MODE.UNAUTHETICATED);
        factory.setConverter(CONVERTER);
        factory.setOperation(OPERATION);
        
        final Object test = new Object();
        final TestCallback callback =
                new TestCallback() {
            @Override
            public void interactionDone(Object object) {
                setSuccess(object.equals(test));
                if (!isSuccess()) {
                    setFailureReason("Return object is not euqal to the input");
                }
                signal.countDown();
            }
            
            @Override
            public void failure(String errorMessage) {
                setSuccess(false);
                setFailureReason("Interaction Done callback expected");
                signal.countDown();
            }
        };
        factory.setCallback(callback);
        OperationExecutor.execute(test, factory.build());
        
        try {
            signal.await();
            if (!callback.isSuccess()) {
                fail(callback.getFailureReason());
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
    
    
    public void testExecuteThrowScrumToolException() {
        //http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
        final CountDownLatch signal = new CountDownLatch(1);
        DSExecArgs.Factory<Object, Object, Object> factory = new Factory<Object, Object, Object>(MODE.UNAUTHETICATED);
        factory.setConverter(CONVERTER);
        factory.setOperation(OPERATIONEXCEPTION);
        
        final Object test = new Object();
        final TestCallback callback =
                new TestCallback() {
            @Override
            public void interactionDone(Object object) {
                setSuccess(false);
                setFailureReason("Expected a failure");
                signal.countDown();
            }
            
            @Override
            public void failure(String errorMessage) {
                setSuccess(true);
                signal.countDown();
            }
        };
        factory.setCallback(callback);
        OperationExecutor.execute(test, factory.build());
        
        try {
            signal.await();
            if (!callback.isSuccess()) {
                fail(callback.getFailureReason());
            }
        } catch (InterruptedException e) {
            fail();
        }
    }
}
