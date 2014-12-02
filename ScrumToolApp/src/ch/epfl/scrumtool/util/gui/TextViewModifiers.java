package ch.epfl.scrumtool.util.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.entityNameIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.updateTextViewAfterValidityCheck;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ch.epfl.scrumtool.R;

/**
 * @author sylb
 */
public class TextViewModifiers {

    /**
     * @author sylb
     */
    public interface PopupCallback<A> {
        void onModified(A userInput);
    }
    
    public static void modifyText(final Activity parent, final String fieldName,
            final String oldValue, final PopupCallback<String> callback) {

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
                        boolean nameIsValid = entityNameIsValid(userInput);
                        
                        updateTextViewAfterValidityCheck(userInput, nameIsValid, parent.getResources());
                        if (nameIsValid) {
                            callback.onModified(userInput.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        
        alertDialog.show();
    }
    
    public static void modifyEstimation(final Activity parent,
            final Float oldValue, final PopupCallback<Float> callback) {

        LayoutInflater inflater = LayoutInflater.from(parent);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null); // FIXME illegal

        final AlertDialog alertDialog = new AlertDialog.Builder(parent)
            .setView(popupView)
            .setTitle("Enter the new estimation : ")
            .setPositiveButton(android.R.string.ok, null)
            .create();

        final EditText userInput = (EditText) popupView.findViewById(R.id.popup_user_input);
        userInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        userInput.setText(oldValue.toString());

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        callback.onModified(Float.parseFloat(userInput.getText().toString()));
                        alertDialog.dismiss();
                    }
                });
            }
        });
        
        alertDialog.show();
    }
}
