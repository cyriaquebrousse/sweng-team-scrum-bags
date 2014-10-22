package ch.epfl.scrumtool.entity;

/**
 * @author ketsio
 */
public interface IssueInterface {
	
	String getName();
	String getDescription();
	
	/**
	 * Get the estimation of the issue (unitless)
	 * @return the estimation
	 */
	float getEstimation();
	
	PlayerInterface getAssignedPlayer();
	
}
