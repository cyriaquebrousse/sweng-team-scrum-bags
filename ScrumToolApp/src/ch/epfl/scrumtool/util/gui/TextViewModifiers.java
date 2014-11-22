/**
 * 
 */
package ch.epfl.scrumtool.util.gui;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * @author sylb
 *
 */
public class TextViewModifiers {

    public interface PopupCallback  {
        void onModified(String userInput);
    }
    
    public static void modifyText(Activity parent, final PopupCallback callback) {

        LayoutInflater inflater = LayoutInflater.from(parent);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent);

        alertDialogBuilder.setView(popupView);

        final EditText userInput = (EditText) popupView.findViewById(R.id.popup_user_input);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                callback.onModified(userInput.getText().toString());

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
