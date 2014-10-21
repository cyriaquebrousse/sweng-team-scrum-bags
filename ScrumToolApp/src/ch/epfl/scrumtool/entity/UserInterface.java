package ch.epfl.scrumtool.entity;

import java.util.Set;

/**
 * @author ketsio
 *
 */
public interface UserInterface {

	long getId();
	String getName();
	String getUsername();
	
	Set<ProjectInterface> getProjects();
}
