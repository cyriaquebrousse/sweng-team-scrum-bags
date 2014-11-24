package ch.epfl.scrumtool.gui;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
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
 * @author Cyriaque Brousse
 * @author sylb
 */
public class ProjectPlayerListActivity extends BaseListMenuActivity<Player>
        implements OnMenuItemClickListener {

    private static final int MAX_EMAIL_LENGTH = 255;
    private static final int MIN_EMAIL_LENGTH = 4;

    private Project project;
    private ListView listView;
    private PlayerListAdapter adapter;

    private SwipeRefreshLayout listViewLayout;

    private Callback<List<Player>> callback = new DefaultGUICallback<List<Player>>(this) {
        @Override
        public void interactionDone(final List<Player> playerList) {
            listViewLayout.setRefreshing(false);

            adapter = new PlayerListAdapter(ProjectPlayerListActivity.this, playerList);
            listView = (ListView) findViewById(R.id.project_playerlist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                    Intent openUserOverviewIntent = new Intent(view.getContext(), ProfileOverviewActivity.class);

                    User user = playerList.get(position).getUser();
                    openUserOverviewIntent.putExtra(User.SERIALIZABLE_NAME, user);
                    startActivity(openUserOverviewIntent);
                }
            });
            listView.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                    Dialogs.showRoleEditDialog(ProjectPlayerListActivity.this, new DialogCallback<Role>() {
                        @Override
                        public void onSelected(Role selected) {
                            adapter.setRole(selected, view, listView);
                            Player player = adapter .getItem(position);
                            Player.Builder builder = new Player.Builder(player);
                            builder.setRole(selected);
                            builder.build().update(null, new Callback<Boolean>() {
                                @Override
                                public void interactionDone(Boolean object) {
                                    // TODO Auto-generated
                                    // method stub
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_player_list);

        Project project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        if (project != null) {
            this.project = project;
            this.setTitle(project.getName());
        } else {
            throw new NullPointerException("serializableExtra Project.SERIALIZABLE_NAME == null");
        }
        
        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_project_playerlist);
        onCreateSwipeToRefresh(listViewLayout);
    }
    
    private void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                project.loadPlayers(callback);
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        project.loadPlayers(callback);
    }

    private void insertPlayer(String userEmail, Role role) {
        listViewLayout.setRefreshing(true);
        project.addPlayer(userEmail, role, new DefaultGUICallback<Player>(this) {
            @Override
            public void interactionDone(Player player) {
                listViewLayout.setRefreshing(false);
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
                    Toast.makeText(ProjectPlayerListActivity.this,
                            "Could not delete player", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private boolean emailIsValid(String email) {
        return email != null &&
                email.length() > MIN_EMAIL_LENGTH &&
                email.contains("@") &&
                email.length() < MAX_EMAIL_LENGTH;
    }

    @Override
    void openEditElementActivity(Player optionalElementToEdit) {
        LayoutInflater inflater = LayoutInflater.from(ProjectPlayerListActivity.this);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(ProjectPlayerListActivity.this)
                .setView(popupView)
                .setTitle("Enter the new user's email address : ")
                .setPositiveButton(android.R.string.ok, null).create();

        final EditText userInput = (EditText) popupView.findViewById(R.id.popup_user_input);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        InputVerifiers.updateTextViewAfterValidityCheck(
                                userInput, emailIsValid(userInput.getText().toString()),
                                ProjectPlayerListActivity.this.getResources());
                        if (emailIsValid(userInput.getText().toString())) {
                            insertPlayer(userInput.getText().toString(),
                                    Role.INVITED);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }
}
