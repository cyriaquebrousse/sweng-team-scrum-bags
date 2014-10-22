/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Vincent
 * 
 */
public class Project implements ProjectInterface {

    private String name;
    private String description;
    private Player admin;
    private final Set<PlayerInterface> players;
    private final Set<TaskInterface> backlog;
    
	/**
	 * @param name
	 * @param description
	 * @param admin
	 * @param players
	 * @param backlog
	 */
	public Project(String name, String description, Player admin,
			Set<PlayerInterface> players, Set<TaskInterface> backlog) {
		super();
		this.name = name;
		this.description = description;
		this.admin = admin;
		this.players = new HashSet<>(players);
		this.backlog = new HashSet<>(backlog);
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getChangesCount(UserInterface user) {
		return 4; // TODO implement changes count
	}

	@Override
	public Set<TaskInterface> getBacklog() {
		return backlog;
	}

	@Override
	public Set<PlayerInterface> getPlayers() {
		return players;
	}

	@Override
	public PlayerInterface getAdmin() {
		return admin;
	}


}
