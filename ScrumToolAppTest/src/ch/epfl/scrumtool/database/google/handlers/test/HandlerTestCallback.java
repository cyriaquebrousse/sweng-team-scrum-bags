package ch.epfl.scrumtool.database.google.handlers.test;

import java.util.concurrent.CountDownLatch;
import ch.epfl.scrumtool.database.Callback;

/**
 * 
 * @author aschneuw
 *
 * @param <A>
 */
abstract class HandlerTestCallback<A> implements Callback<A> {

    public HandlerTestCallback(CountDownLatch signal) {
        this.signal = signal;
    }

    private final CountDownLatch signal;
    private boolean success;

    public boolean hasSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void interactionDone(A object) {
        signal.countDown();

    }

    @Override
    public void failure(String errorMessage) {
        success = false;
        signal.countDown();
    }
}