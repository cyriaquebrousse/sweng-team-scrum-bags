package ch.epfl.scrumtool.gui.components;

import android.content.Context;
import android.widget.Toast;
import ch.epfl.scrumtool.database.Callback;

/**
 * 
 * @author aschneuw
 *
 * @param <A>
 */


public abstract class DefaultGUICallback<A> implements Callback<A> {
    private final Context context;
    
    public DefaultGUICallback(final Context context) {
        this.context = context;
    }
    
    @Override
    public void failure(String errorMessage) {
        if (context != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
