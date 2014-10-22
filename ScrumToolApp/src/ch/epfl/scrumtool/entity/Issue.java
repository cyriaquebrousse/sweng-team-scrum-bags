package ch.epfl.scrumtool.entity;


/**
 * @author ketsio
 *
 */
public class Issue implements IssueInterface {
    
	private String name;
	private String description;
	private float estimation;
    private PlayerInterface player;

    @Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public float getEstimation() {
		return estimation;
	}

	@Override
	public PlayerInterface getAssignedPlayer() {
		return player;
	}

}
