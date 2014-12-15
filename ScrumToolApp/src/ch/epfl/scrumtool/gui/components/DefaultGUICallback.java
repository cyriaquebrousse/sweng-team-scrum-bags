package ch.epfl.scrumtool.gui.components;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import ch.epfl.scrumtool.database.Callback;

/**
 * @author aschneuw
 * @author Cyriaque Brousse
 *
 * @param <A>
 */

public abstract class DefaultGUICallback<A> implements Callback<A> {
    private final Context context;
    private final View viewToReactivate;

    /**
     * @param context
     *            context in which to display possible failures
     */
    public DefaultGUICallback(final Context context) {
        this.context = context;
        this.viewToReactivate = null;
    }
    
    /**
     * @param context
     *            context in which to display possible failures
     * @param viewToReactivate
     *            view to reactivate in case of failure
     */
    public DefaultGUICallback(final Context context, View viewToReactivate) {
        this.context = context;
        this.viewToReactivate = viewToReactivate;
    }

    @Override
    public void failure(String errorMessage) {
        if (context != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            if (viewToReactivate != null) {
                viewToReactivate.setEnabled(true);
            }
        }
    }
}
