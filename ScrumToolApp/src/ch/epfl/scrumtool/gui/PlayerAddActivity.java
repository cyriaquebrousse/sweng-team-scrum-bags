package ch.epfl.scrumtool.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.widgets.RoleSticker;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import static ch.epfl.scrumtool.util.InputVerifiers.emailIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.updateTextViewAfterValidityCheck;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity used to add a Player to a Project
 * @author vincent
 *
 */
public class PlayerAddActivity extends BaseMenuActivity {
    
    private static final int EMAIL_INDEX = 3;
    
    private Project project;
    private Role role;
    
    private EditText emailAddressView;
    private RoleSticker roleStickerView;
    private Button displayContactsView;
    private Button invitePlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_add);
        
        // initialize project
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("project cannot be null", project);
        
        // initialize views
        emailAddressView = (EditText) findViewById(R.id.player_address_add);
        roleStickerView = (RoleSticker) findViewById(R.id.player_role_sticker);
        invitePlayerButton = (Button) findViewById(R.id.player_add_button);
        
        role = Role.STAKEHOLDER;
        roleStickerView.setRole(role);
        roleStickerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showRoleEditDialog(PlayerAddActivity.this, new DialogCallback<Role>() {

                    @Override
                    public void onSelected(Role selected) {
                        role = selected;
                        roleStickerView.setRole(selected);
                    }
                });
            }
        });
        
        displayContactsView = (Button) findViewById(R.id.player_select_contact_button);
        displayContactsView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                displayContacts();
            }
        });
    }
    
    private void displayContacts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerAddActivity.this);
        final String[] contacts = getContactsEmails();
        
        builder.setTitle("Contacts");
        builder.setItems(contacts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailAddressView.setText(contacts[which]);
            }
        });
        
        builder.create().show();
    }
    
    public void invitePlayer(View view) {
        String userInput = emailAddressView.getText().toString();
        updateTextViewAfterValidityCheck(emailAddressView, emailIsValid(userInput),
                PlayerAddActivity.this.getResources());
        if (emailIsValid(userInput)) {
            project.addPlayer(userInput, role, new DefaultGUICallback<Player>(this, invitePlayerButton) {

                @Override
                public void interactionDone(Player object) {
                    PlayerAddActivity.this.finish();
                }
            });
        }
    }
    
    /**
     * Retreives the phones contacts that have an email address
     * 
     * @return the list of contacts
     */
    private String[] getContactsEmails() {
        //Credits go to 
        //http://stackoverflow.com/questions/10117049/get-only-email-address-from-contact-list-android
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = getBaseContext();
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[] { 
            ContactsContract.RawContacts._ID, 
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.CommonDataKinds.Email.DATA, 
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN " 
                + ContactsContract.Contacts.DISPLAY_NAME 
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, " 
                + ContactsContract.Contacts.DISPLAY_NAME 
                + ", " 
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projection, filter, null, order);
        if (cursor.moveToFirst()) {
            do {
                // names comes in hand sometimes
                //String name = cursor.getString(1);
                String emaillAddress = cursor.getString(EMAIL_INDEX);

                // keep unique only
                if (emlRecsHS.add(emaillAddress.toLowerCase(Locale.US))) {
                    emlRecs.add(emaillAddress.toLowerCase(Locale.US));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Collections.sort(emlRecs.subList(0, emlRecs.size()));
        String[] emails = new String[emlRecs.size()];
        emlRecs.toArray(emails);
        return emails;
    }
}
