package ch.epfl.scrumtool.entity;

import java.util.Set;

/**
 * @author ketsio
 */
public interface TaskInterface {
	
	String getName();
	String getDescription();
	
	Set<IssueInterface> getIssues();
}
