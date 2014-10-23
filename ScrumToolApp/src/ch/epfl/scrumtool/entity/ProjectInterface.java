package ch.epfl.scrumtool.entity;

import java.util.Set;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author ketsio
 *
 */
public interface ProjectInterface {
    String getName();
    String getDescription();
    int getChangesCount(UserInterface user);

    Set<TaskInterface> getBacklog();
    Set<PlayerInterface> getPlayers();
    PlayerInterface getAdmin();
    
    /**
     * Given a user, returns its role for the current project if the user is
     * indeed a player of this project. Throws an exception if not
     * @param user
     * @return the role of the user
     * @throws NotAPlayerOfThisProjectException if the user is not a player
     * of this project
     */
    Role getRoleFor(UserInterface user) throws NotAPlayerOfThisProjectException;
}
