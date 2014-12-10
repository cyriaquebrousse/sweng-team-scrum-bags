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
import static ch.epfl.scrumtool.util.InputVerifiers.verifyEmailIsValid;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

/**
 * Activity used to add a Player to a Project
 * @author vincent
 *
 */
public class PlayerAddActivity extends BaseEditMenuActivity {
    
    private static final int EMAIL_INDEX = 3;
    
    private Project project;
    private Role role;
    private ArrayList<String> contactsAddresses;
    
    private AutoCompleteTextView emailAddressView;
    private RoleSticker roleStickerView;
    private Button displayContactsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_add);
        
        // initialize project
        project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("project cannot be null", project);
        
        // initialize views
        emailAddressView = (AutoCompleteTextView) findViewById(R.id.player_address_add);
        roleStickerView = (RoleSticker) findViewById(R.id.player_role_sticker);
        
        // initialize RoleSticker
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
        
        // Initialize contacts
        contactsAddresses = getContactsEmails();
        displayContactsView = (Button) findViewById(R.id.player_select_contact_button);
        displayContactsView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                displayContacts();
            }
        });
        
        //autocompletion
        emailAddressView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, contactsAddresses));
        
    }
    
    @Override
    protected void saveElement() {
        final View next = findViewById(Menu.FIRST);
        String userInput = emailAddressView.getText().toString();
        verifyEmailIsValid(emailAddressView, PlayerAddActivity.this.getResources());
        if (emailIsValid(userInput)) {
            project.addPlayer(userInput, role, new DefaultGUICallback<Player>(this, next) {

                @Override
                public void interactionDone(Player object) {
                    PlayerAddActivity.this.finish();
                }
            });
        }
    }

    private void displayContacts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayerAddActivity.this);
        final String[] contacts = new String[contactsAddresses.size()];
        contactsAddresses.toArray(contacts);
        
        builder.setTitle("Contacts");
        builder.setItems(contacts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailAddressView.setText(contacts[which]);
            }
        });
        
        builder.create().show();
    }
    
    /**
     * Retreives the phones contacts that have an email address
     * 
     * @return the list of contacts
     */
    private ArrayList<String> getContactsEmails() {
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
        return emlRecs;
    }
}
