/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import android.os.AsyncTask;

/**
 * @author aschneuw
 * 
 */
public final class DSOperationExecutor {
    @SuppressWarnings("unchecked")
    public static <A, B, C> void execute(final A a, final DSExecArgs<A, B, C> args) {
        AsyncTask<A, Void, B> task = new AsyncTask<A, Void, B>() {

            @Override
            protected B doInBackground(final A... params) {
                B returnObject = null;
                try {
                    returnObject = args.getOperation().execute(params[0]);
                } catch (IOException e) {
                    args.getCallback().failure("FAIL");
                }
                return returnObject;
            }

            @Override
            protected void onPostExecute(final B result) {
                if (result == null) {
                    args.getCallback().failure("Invalid result");
                } else {
                    C convertedResult = args.getConverter().convert(result);
                    args.getCallback().interactionDone(convertedResult);
                }
            }
        };
        task.execute(a);
    }
}
