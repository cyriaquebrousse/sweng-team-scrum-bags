package ch.epfl.scrumtool.entity;

import java.util.Set;

/**
 * @author ketsio
 */
public interface TaskInterface {
	
	String getName();
	String getDescription();
	Status getStatus();
	
	Set<IssueInterface> getIssues();
}
