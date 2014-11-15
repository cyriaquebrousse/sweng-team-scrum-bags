package ch.epfl.scrumtool.gui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import ch.epfl.scrumtool.entity.Priority;

/**
 * @author Cyriaque Brousse
 */
public class Dialogs {
    
    /**
     * @author Cyriaque Brousse
     * @param <E> entity we deal with here
     */
    public interface DialogCallback<E> {
        void onSelected(E selected);
    }

    public static void showTaskPriorityEditDialog(Activity parent, final DialogCallback<Priority> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        String[] priorities = initPriorities();
        
        builder.setTitle("Set priority");
        builder.setItems(priorities, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onSelected(Priority.values()[which]);
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
    private static String[] initPriorities() {
        String[] priorities = new String[Priority.values().length];
        for (int i = 0; i < priorities.length; i++) {
            priorities[i] = Priority.values()[i].toString();
        }
        return priorities;
    }
    
    
}
