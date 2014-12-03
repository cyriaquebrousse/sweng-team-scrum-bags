package ch.epfl.scrumtool.gui.components;

import android.content.Context;
import android.widget.Button;
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
    private final Button buttonToReactivate;

    /**
     * @param context
     *            context in which to display possible failures
     */
    public DefaultGUICallback(final Context context) {
        this.context = context;
        this.buttonToReactivate = null;
    }
    
    /**
     * @param context
     *            context in which to display possible failures
     * @param buttonToReactivate
     *            button to reactivate in case of failure
     */
    public DefaultGUICallback(final Context context, Button buttonToReactivate) {
        this.context = context;
        this.buttonToReactivate = buttonToReactivate;
    }

    @Override
    public final void failure(String errorMessage) {
        if (context != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            if (buttonToReactivate != null) {
                buttonToReactivate.setEnabled(true);
            }
        }
    }
}
