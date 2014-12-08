package ch.epfl.scrumtool.util.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.verifyNameIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyDescriptionIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.verifyEstimationIsValid;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.util.InputVerifiers;

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
     * 
     * @author sylb
     *
     * @param <String>
     */
    public interface PopupCallbackProfile<String> {
        void onModified(String firstname, String lastName);
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
                                fieldIsValid = verifyNameIsValid(userInput, parent.getResources());
                                break;
                            case DESCRIPTIONFIELD:
                            default:
                                fieldIsValid = verifyDescriptionIsValid(userInput, parent.getResources());
                                break;
                        }
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
        if (!oldValue.toString().equals("0.0")) {
            userInput.setText(oldValue.toString());
        }

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        boolean estimationIsValid = verifyEstimationIsValid(userInput, parent.getResources());
                        if (estimationIsValid) {
                            callback.onModified(InputVerifiers.sanitizeFloat(userInput.getText().toString()));
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

    public static void modifyNameOnProfile(final Activity parent, final String oldFirstNameValue,
            final String oldLastNameValue, final PopupCallbackProfile<String> callback) {

        LayoutInflater inflater = LayoutInflater.from(parent);
        View popupView = inflater.inflate(R.layout.popupmodifiersprofile, null); // FIXME illegal

        final AlertDialog alertDialog = new AlertDialog.Builder(parent)
            .setView(popupView)
            .setTitle("Enter the new values for your first and last names: ")
            .setPositiveButton(android.R.string.ok, null)
            .create();

        final EditText userInputFirstName = (EditText) popupView.findViewById(R.id.popup_user_input_first_name);
        final EditText userInputLastName = (EditText) popupView.findViewById(R.id.popup_user_input_last_name);

        userInputFirstName.setText(oldFirstNameValue);
        userInputLastName.setText(oldLastNameValue);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        boolean firstNameIsValid = verifyNameIsValid(userInputFirstName, parent.getResources());
                        boolean lastNameIsValid = verifyNameIsValid(userInputLastName, parent.getResources());
                        if (firstNameIsValid && lastNameIsValid) {
                            callback.onModified(userInputFirstName.getText().toString(),
                                    userInputLastName.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }
}
