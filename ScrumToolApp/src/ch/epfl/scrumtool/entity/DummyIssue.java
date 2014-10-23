package ch.epfl.scrumtool.entity;

import java.util.List;
import java.util.Set;

/**
 * @author Cyriaque Brousse
 */
@Deprecated
public class DummyIssue implements IssueInterface {

    private String name;
    private String description;
    private Status status;
    private float estimation;

    public DummyIssue(String name, String description, Status status, float estimation) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.estimation = estimation;
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
    public Status getStatus() {
        return status;
    }

    @Override
    public float getEstimation() {
        return estimation;
    }

    @Override
    public PlayerInterface getAssignedPlayer() {
        return new PlayerInterface() {

            @Override
            public Role getRole() {
                return Role.DEVELOPER;
            }

            @Override
            public UserInterface getAccount() {
                return new UserInterface() {

                    @Override
                    public String getUsername() {
                        return "smith";
                    }

                    @Override
                    public Set<ProjectInterface> getProjects() {
                        return null;
                    }

                    @Override
                    public String getName() {
                        return "John Smith";
                    }

                    @Override
                    public long getId() {
                        return Long.MAX_VALUE;
                    }

                    @Override
                    public String getEmail() {
                        return "john@smith.ch";
                    }

                    @Override
                    public List<ProjectInterface> getProjectsSharedWith(UserInterface user) {
                        return null;
                    }
                };
            }
        };
    }

}
