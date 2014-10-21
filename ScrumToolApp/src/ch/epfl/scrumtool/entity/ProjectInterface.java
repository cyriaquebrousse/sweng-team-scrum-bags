package ch.epfl.scrumtool.entity;

import java.util.Set;

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
}
