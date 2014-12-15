package ch.epfl.scrumtool.database.google.operations;

import android.os.AsyncTask;
import android.util.Log;
import ch.epfl.scrumtool.exception.ScrumToolException;

/**
 * Executes a ScrumToolOperation and converting its result to the callback return value
 * and finally calls the callback
 * 
 * @author aschneuw
 */
public final class OperationExecutor {
    public static final String TAG = "Operation Executor";
    
    @SuppressWarnings("unchecked")
    public static <A, B, C> void execute(final A a, final DSExecArgs<A, B, C> args) {
        AsyncTask<A, Void, TaskResult<B>> task = new AsyncTask<A, Void, TaskResult<B>>() {

            @Override
            protected TaskResult<B> doInBackground(final A... params) {
                try {
                    B serverResult = args.getOperation().execute(params[0]);
                    return new TaskResult<B>(serverResult);
                } catch (ScrumToolException e) {
                    Log.e(TAG, "Execution failed", e);
                    return new TaskResult<B>(e);
                }
            }

            @Override
            protected void onPostExecute(final TaskResult<B> result) {
                if (result.isValid()) {
                    C convertedResult = args.getConverter().convert(result.getResult());
                    args.getCallback().interactionDone(convertedResult);
                } else {
                    args.getCallback().failure(result.getException().getGUIMessage());
                }
            }
        };
        task.execute(a);
    }
}
