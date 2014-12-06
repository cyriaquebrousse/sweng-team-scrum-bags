package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.InputVerifiers.emailIsValid;
import static ch.epfl.scrumtool.util.InputVerifiers.updateTextViewAfterValidityCheck;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.PlayerListAdapter;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.util.gui.Dialogs;
import ch.epfl.scrumtool.util.gui.Dialogs.DialogCallback;

/**
 * @author Cyriaque Brousse
 * @author sylb
 */
public class ProjectPlayerListActivity extends BaseListMenuActivity<Player> implements OnMenuItemClickListener {

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
                    User user = playerList.get(position).getUser();
                    Intent openUserOverviewIntent = null;
                    try {
                        if (user.getEmail().equals(Session.getCurrentSession().getUser().getEmail())) {
                            openUserOverviewIntent = new Intent(view.getContext(), MyProfileOverviewActivity.class);
                        } else {
                            openUserOverviewIntent = new Intent(view.getContext(), OthersProfileOverviewActivity.class);
                        }
                    } catch (NotAuthenticatedException e) {
                        Session.relogin(getParent());
                    }
                    openUserOverviewIntent.putExtra(User.SERIALIZABLE_NAME, user);
                    startActivity(openUserOverviewIntent);
                }
            });
            registerForContextMenu(listView);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_player_list);

        Project project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent object cannot be null", project);
        
        this.project = project;
        this.setTitle(project.getName());
        
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
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_entitylist_context, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_entity_edit:
                setRole(adapter.getItem(info.position));
                return true;
            case R.id.action_entity_delete:
                deletePlayer(adapter.getItem(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
    
    private void setRole(final Player player) {
        Dialogs.showRoleEditDialog(ProjectPlayerListActivity.this, new DialogCallback<Role>() {
            @Override
            public void onSelected(final Role selected) {
                Player.Builder builder = new Player.Builder(player);
                builder.setRole(selected);
                builder.build().update(null, new Callback<Void>() {
                    @Override
                    public void interactionDone(Void object) {
                        project.loadPlayers(callback);
                    }

                    @Override
                    public void failure(String errorMessage) {
                        Log.d("test", "failure");
                        //TODO error handling?
                    }
                });
            }
        });
    }

    private void deletePlayer(final Player player) {
        player.remove(new DefaultGUICallback<Void>(this) {
            @Override
            public void interactionDone(Void a) {
                adapter.remove(player);
            }
        });
    }

    @Override
    void openEditElementActivity(Player optionalElementToEdit) {
        //TODO don't use StakeHolder, implement a way to select the role
        LayoutInflater inflater = LayoutInflater.from(ProjectPlayerListActivity.this);
        View popupView = inflater.inflate(R.layout.popupmodifiers, null); // FIXME illegal

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
                        updateTextViewAfterValidityCheck(userInput, emailIsValid(userInput.getText().toString()),
                                ProjectPlayerListActivity.this.getResources());
                        if (emailIsValid(userInput.getText().toString())) {
                            insertPlayer(userInput.getText().toString(), Role.STAKEHOLDER);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }
}
