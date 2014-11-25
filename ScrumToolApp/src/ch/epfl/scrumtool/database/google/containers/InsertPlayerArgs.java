package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;

/**
 * We need a container because of the way we implemented the Handlers to make
 * the communication with the database. They are just wrappers for more arguments.
 * 
 * @author vincent
 *
 */
public class InsertPlayerArgs {
    private Project project;
    private String userEmail;
    private Role role;
    
    public InsertPlayerArgs(Project project, String userEmail, Role role) {
        this.project = project;
        this.userEmail = userEmail;
        this.role = role;
    }
    
    public String getProjectKey() {
        return project.getKey();
    }
    
    public String getUserEmail() {
        return userEmail;
    }

    public String getRole() {
        return role.name();
    }
}
