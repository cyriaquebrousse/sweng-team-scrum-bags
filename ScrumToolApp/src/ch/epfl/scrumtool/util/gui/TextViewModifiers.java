package ch.epfl.scrumtool.util.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.entityNameIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.textEditNonNullNotEmpty;
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
    
    /**
     * @author sylb
     */
    public enum FieldType {
        
        NAMEFIELD("name"),
        DESCRIPTIONFIELD("description"),
        OTHER("value");
        
        private String value;
        
        FieldType(String string) {
            this.value = string;
        }
        
        public FieldType setText(String text) {
            this.value = text;
            return this;
        }
    };
    
    public static void modifyText(final Activity parent, final FieldType fieldType,
            final String oldValue, final PopupCallback<String> callback) {

        LayoutInflater inflater = LayoutInflater.from(parent);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null); // FIXME illegal

        final AlertDialog alertDialog = new AlertDialog.Builder(parent)
            .setView(popupView)
            .setTitle("Enter the new " + fieldType.value + ": ")
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
                        boolean fieldIsValid;
                        switch (fieldType) {
                            case NAMEFIELD:
                                fieldIsValid = entityNameIsValid(userInput);
                                break;
                            case DESCRIPTIONFIELD:
                                fieldIsValid = textEditNonNullNotEmpty(userInput);
                            default:
                                fieldIsValid = textEditNonNullNotEmpty(userInput);
                                break;
                        }
                        
                        updateTextViewAfterValidityCheck(userInput, fieldIsValid, parent.getResources());
                        if (fieldIsValid) {
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
