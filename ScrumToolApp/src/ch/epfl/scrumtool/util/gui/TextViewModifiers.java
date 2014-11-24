package ch.epfl.scrumtool.util.gui;

import ch.epfl.scrumtool.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author sylb
 */
public class TextViewModifiers {

    /**
     * @author sylb
     */
    public interface PopupCallback  {
        void onModified(String userInput);
    }
    
    public static void modifyText(final Activity parent, final String fieldName,
            final String oldValue, final PopupCallback callback) {

        LayoutInflater inflater = LayoutInflater.from(parent);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null); // FIXME illegal

        final AlertDialog alertDialog = new AlertDialog.Builder(parent)
            .setView(popupView)
            .setTitle("Enter the new " + fieldName + ": ")
            .setPositiveButton(android.R.string.ok, null)
            .create();

        final EditText userInput = (EditText) popupView.findViewById(R.id.popup_user_input);
        userInput.setText(oldValue);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        InputVerifiers.updateTextViewAfterValidityCheck(userInput,
                                nameIsValid(userInput), parent.getResources());
                        if (nameIsValid(userInput)) {
                            callback.onModified(userInput.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        
        alertDialog.show();
    }
    
    private static boolean nameIsValid(EditText editText) {
        String stringValue = editText.getText().toString();
        return stringValue != null && stringValue.length() > 0
                && stringValue.length() < 50; // TODO put a meaningful value
    }

}
