package ch.epfl.scrumtool.gui;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;
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
        listViewLayout.setRefreshing(true);
        new AlertDialog.Builder(this).setTitle("Delete Player")
            .setMessage("Do you really want to delete this Player? "
                    + "This will remove the Player from the project and unlink it from its issues.")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Context context = ProjectPlayerListActivity.this;
                    player.remove(new DefaultGUICallback<Void>(context) {
                        @Override
                        public void interactionDone(Void v) {
                            Toast.makeText(context , "Player deleted", Toast.LENGTH_SHORT).show();
                            listViewLayout.setRefreshing(false);
                            adapter.remove(player);
                        }
                    });
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listViewLayout.setRefreshing(false);
                }
            }).show();
    }

    @Override
    void openEditElementActivity(Player optionalElementToEdit) {
        Intent openPlayerAddIntent = new Intent(this, PlayerAddActivity.class);
        openPlayerAddIntent.putExtra(Project.SERIALIZABLE_NAME, project);
        startActivity(openPlayerAddIntent);
    }
}
