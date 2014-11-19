/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import android.os.AsyncTask;
import ch.epfl.scrumtool.exception.ScrumToolException;

/**
 * @author aschneuw
 * 
 */
public final class DSOperationExecutor {
    @SuppressWarnings("unchecked")
    public static <A, B, C> void execute(final A a, final DSExecArgs<A, B, C> args) {
        AsyncTask<A, Void, TaskResult<B>> task = new AsyncTask<A, Void, TaskResult<B>>() {

            @Override
            protected TaskResult<B> doInBackground(final A... params) {
                try {
                    B serverResult = args.getOperation().execute(params[0]);
                    return new TaskResult<B>(serverResult);
                    
                } catch (ScrumToolException e) {
                    e.printStackTrace();
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
