package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import ch.epfl.scrumtool.R;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Player;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.gui.components.DefaultGUICallback;
import ch.epfl.scrumtool.gui.components.adapters.PlayerListAdapter;
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

    private Callback<List<Player>> loadPlayersCallback = new DefaultGUICallback<List<Player>>(this) {
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
                    Intent openUserOverviewIntent = new Intent(view.getContext(), OthersProfileOverviewActivity.class);
                    try {
                        if (user.getEmail().equals(Session.getCurrentSession().getUser().getEmail())) {
                            openUserOverviewIntent = new Intent(view.getContext(), MyProfileOverviewActivity.class);
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
        
        @Override
        public void failure(String errorMessage) {
            listViewLayout.setRefreshing(false);
            super.failure(errorMessage);
        }
    };
    
    private Callback<Void> setAdminOrRoleCallback = new DefaultGUICallback<Void>(ProjectPlayerListActivity.this) {
        @Override
        public void interactionDone(Void object) {
            project.loadPlayers(loadPlayersCallback);
        }
        
        @Override
        public void failure(String errorMessage) {
            listViewLayout.setRefreshing(false);
            super.failure(errorMessage);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_player_list);

        Project project = (Project) getIntent().getSerializableExtra(Project.SERIALIZABLE_NAME);
        throwIfNull("Parent object cannot be null", project);
        
        this.project = project;
        
        listViewLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_update_project_playerlist);
        onCreateSwipeToRefresh(listViewLayout);
    }
    
    protected void onCreateSwipeToRefresh(final SwipeRefreshLayout refreshLayout) {
        super.onCreateSwipeToRefresh(refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                project.loadPlayers(loadPlayersCallback);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewLayout.setRefreshing(true);
        project.loadPlayers(loadPlayersCallback);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_player_list_context, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_player_edit_role:
                setRole(adapter.getItem(info.position));
                return true;
            case R.id.action_player_delete:
                deletePlayer(adapter.getItem(info.position));
                return true;
            case R.id.action_player_set_admin:
                setAdmin(adapter.getItem(info.position));
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    private void setAdmin(final Player item) {
        new AlertDialog.Builder(ProjectPlayerListActivity.this)
            .setTitle("Change admin")
            .setMessage("A project can only have one Administrator. " 
                + "If you continue you will loose your admin rights.")
            .setPositiveButton("Continue", new OnClickListener() {
            
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(true);
                    item.setAsAdmin(setAdminOrRoleCallback);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
    }

    private void setRole(final Player player) {
        Dialogs.showRoleEditDialog(ProjectPlayerListActivity.this, new DialogCallback<Role>() {
            @Override
            public void onSelected(final Role selected) {
                listViewLayout.setRefreshing(true);
                Player.Builder builder = new Player.Builder(player);
                builder.setRole(selected);
                builder.build().update(new DefaultGUICallback<Void>(ProjectPlayerListActivity.this) {
                    @Override
                    public void interactionDone(Void object) {
                        project.loadPlayers(loadPlayersCallback);
                    }
                });
            }
        });
    }

    private void deletePlayer(final Player player) {
        new AlertDialog.Builder(this).setTitle("Delete Player")
            .setMessage("Do you really want to delete this Player? "
                    + "This will remove the Player from the project and unlink it from its issues.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(true);
                    final Context context = ProjectPlayerListActivity.this;
                    player.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            listViewLayout.setRefreshing(false);
                            Toast.makeText(context , "Player deleted", Toast.LENGTH_SHORT).show();
                            adapter.remove(player);
                        }
                        
                        @Override
                        public void failure(String errorMessage) {
                            listViewLayout.setRefreshing(false);
                            super.failure(errorMessage);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }

    @Override
    protected void openEditElementActivity(Player optionalElementToEdit) {
        Intent openPlayerAddIntent = new Intent(this, PlayerAddActivity.class);
        openPlayerAddIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openPlayerAddIntent);
    }
}
