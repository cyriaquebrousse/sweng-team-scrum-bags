package ch.epfl.scrumtool.util.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import ch.epfl.scrumtool.entity.Priority;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.Status;

/**
 * @author Cyriaque Brousse
 */
public final class Dialogs {
    
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
            priorities[i] = InputVerifiers.capitalize(Priority.values()[i].toString());
        }
        return priorities;
    }
    
    public static void showRoleEditDialog(Activity parent, final DialogCallback<Role> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        String[] roles = initRoles();
        
        builder.setTitle("Set role");
        builder.setItems(roles, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onSelected(Role.values()[which]);
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
    private static String[] initRoles() {
        String[] roles = new String[Role.values().length];
        for (int i = 0; i < roles.length; i++) {
            roles[i] = InputVerifiers.capitalize(Role.values()[i].toString());
        }
        return roles;
    }
    
    public static void showStatusEditDialog(Activity parent, final DialogCallback<Status> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        String[] statuses = initStatuses();
        
        builder.setTitle("Set status");
        builder.setItems(statuses, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onSelected(Status.values()[which]);
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
    
    private static String[] initStatuses() {
        String[] statuses = new String[Status.values().length];
        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = InputVerifiers.capitalize(Status.values()[i].toString());
        }
        return statuses;
    }
    
}
