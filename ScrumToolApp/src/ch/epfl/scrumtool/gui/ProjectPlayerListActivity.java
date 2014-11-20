package ch.epfl.scrumtool.gui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.PlayerListAdapter;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;
import ch.epfl.scrumtool.util.gui.InputVerifiers;

/**
 * @author Cyriaque Brousse, sylb
 */
public class ProjectPlayerListActivity extends BaseMenuActivity implements OnMenuItemClickListener {

    private Project project;
    private ListView listView;
    private PlayerListAdapter adapter;

    private EditText newPlayerEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_player_list);

        initProject();
        initAddPlayerBlock();
        
        this.setTitle(project.getName());

        DefaultGUICallback<List<Player>> playersLoaded = new DefaultGUICallback<List<Player>>(this) {
            @Override
            public void interactionDone(final List<Player> playerList) {
                adapter = new PlayerListAdapter(ProjectPlayerListActivity.this, playerList);
                listView = (ListView) findViewById(R.id.project_playerlist);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        Intent openUserOverviewIntent = new Intent(
                                view.getContext(),
                                ProfileOverviewActivity.class);

                        User user = playerList.get(position).getUser();
                        openUserOverviewIntent.putExtra(User.SERIALIZABLE_NAME, user);
                        startActivity(openUserOverviewIntent);
                    }
                });
                listView.setOnItemLongClickListener(new OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                            final View view, final int position, long id) {
                        Dialogs.showRoleEditDialog(ProjectPlayerListActivity.this, new DialogCallback<Role>() {
                            @Override
                            public void onSelected(Role selected) {
                                adapter.setRole(selected, view, listView);
                                Player player = adapter.getItem(position);
                                Player.Builder builder = new Player.Builder(player);
                                builder.setRole(selected);
                                builder.build().update(null, new Callback<Boolean>() {
                                    
                                    @Override
                                    public void interactionDone(Boolean object) {
                                        // TODO Auto-generated method stub
                                        Log.d("test", "success");
                                    }
                                    
                                    @Override
                                    public void failure(String errorMessage) {
                                        Log.d("test", "failure");
                                        
                                    }
                                });
                                
                            }
                        });
                        return true;
                    }
                });
                adapter.notifyDataSetChanged();
            }
        };
        project.loadPlayers(playersLoaded);
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
    }

    public void addPlayer(View view) {
        InputVerifiers.updateTextViewAfterValidityCheck(newPlayerEmailView, emailIsValid(), getResources());

        if (emailIsValid()) {
            String email = newPlayerEmailView.getText().toString();
            Role role = Role.INVITED; // default role for newcomers

            insertPlayer(email, role);
        }
    }

    private void insertPlayer(String userEmail, Role role) {
        project.addPlayer(userEmail, role, new DefaultGUICallback<Player>(this) {
            @Override
            public void interactionDone(Player player) {
                adapter.add(player);
            }
        });
    }

    private void deletePlayer(final Player player) {
        player.remove(new DefaultGUICallback<Boolean>(this) {

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
