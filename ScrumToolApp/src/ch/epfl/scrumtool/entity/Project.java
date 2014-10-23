/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;


/**
 * @author Vincent
 * 
 */
public class Project implements ProjectInterface {

    private String name;
    private String description;
    private PlayerInterface admin;
    private Set<PlayerInterface> players;
    private final Set<TaskInterface> backlog;


    /**
     * @param name
     * @param description
     * @param admin
     * @param players
     * @param backlog
     */
    public Project(String name, String description, PlayerInterface admin,
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
    
    public void setPLayers(Set<PlayerInterface> players) {
        this.players = players;
    }
    
    public void setAdmin(PlayerInterface admin) {
        this.admin = admin;
    }

    @Override
    public Role getRoleFor(UserInterface user) throws NotAPlayerOfThisProjectException {
        return null; // TODO Database Call
    }

}
