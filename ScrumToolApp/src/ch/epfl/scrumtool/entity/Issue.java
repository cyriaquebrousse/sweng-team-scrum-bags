package ch.epfl.scrumtool.entity;

/**
 * @author ketsio
 */
public class Issue implements IssueInterface {

    private String name;
    private String description;
    private float estimation;
    private PlayerInterface player;
    private Status status;

    public Issue(String name, String description, float estimation,
            PlayerInterface player, Status status) {
        this.name = name;
        this.description = description;
        this.estimation = estimation;
        this.player = player;
        this.status = status;
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
    public float getEstimation() {
        return estimation;
    }

    @Override
    public PlayerInterface getAssignedPlayer() {
        return player;
    }

    @Override
    public Status getStatus() {
        return status;
    }

}
