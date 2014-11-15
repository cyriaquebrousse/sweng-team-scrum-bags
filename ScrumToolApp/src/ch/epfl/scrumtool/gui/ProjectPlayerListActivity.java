package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.PlayerListAdapter;
import ch.epfl.scrumtool.gui.util.InputVerifiers;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Cyriaque Brousse
 */
public class ProjectPlayerListActivity extends Activity {

    private Project project;

    private ListView listView;
    private PlayerListAdapter adapter;

    private EditText newPlayerEmailView;
//    private Player.Builder playerBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_player_list);

        initProject();
        initAddPlayerBlock();


        DefaultGUICallback<List<Player>> playersLoaded = new DefaultGUICallback<List<Player>>(this) {
            @Override
            public void interactionDone(final List<Player> playerList) {
                adapter = new PlayerListAdapter(ProjectPlayerListActivity.this, playerList);
                listView = (ListView) findViewById(R.id.project_playerlist);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(ProjectPlayerListActivity.this, "User clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.notifyDataSetChanged();
            }
        };
        Client.getScrumClient().loadPlayers(project.getKey(), playersLoaded);
    }

    private void initProject() {
        Project project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project != null) {
            this.project = project;
        } else {
            Log.e("ProjectPlayerList", "null project passed");
            this.finish();
        }
    }

    private void initAddPlayerBlock() {
        newPlayerEmailView = (EditText) findViewById(R.id.player_list_add_playeremail);

//        playerBuilder = new Player.Builder();
    }

    public void addPlayer(View view) {
        InputVerifiers.updateTextViewAfterValidityCheck(newPlayerEmailView, emailIsValid(), getResources());

        if (emailIsValid()) {
            String email = newPlayerEmailView.getText().toString();
            Role role = Role.INVITED; // default role for newcomers
            
//            User.Builder userBuilder = new User.Builder();
//            userBuilder.setEmail(email);
//            userBuilder.setName(email.toUpperCase()); // TODO real name
//            playerBuilder.setUser(userBuilder.build());
//            playerBuilder.setKey("random player id "+ new Random().nextInt());
//            playerBuilder.setIsAdmin(false);
//            playerBuilder.setRole(Role.DEVELOPER); // TODO real role

            insertPlayer(email, role);
        }
    }

    private void insertPlayer(String userEmail, Role role) {
//        Player player = playerBuilder.build();
        DefaultGUICallback<Player> playerAdded = new DefaultGUICallback<Player>(this) {

            @Override
            public void interactionDone(Player object) {
                adapter.add(object);
            }
        };


        Client.getScrumClient().addPlayerToProject(project.getKey(), userEmail, role,  playerAdded);
    }

    private void deletePlayer(final Player player) {
        Client.getScrumClient().removePlayer(player.getKey(), new DefaultGUICallback<Boolean>(this) {

            @Override
            public void interactionDone(Boolean success) {
                if (success.booleanValue()) {
                    adapter.remove(player);
                } else {
                    Toast.makeText(ProjectPlayerListActivity.this, "Could not delete player",
                            Toast.LENGTH_SHORT).show();
                }                
            }
        });
    }

    private boolean emailIsValid() {
        String email = newPlayerEmailView.getText().toString();
        return email != null && email.length() > 4 && email.contains("@") && email.length() < 255;
    }
}
