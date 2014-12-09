package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.Role;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * We need a container because of the way we implemented the Handlers to make
 * the communication with the database. They are just wrappers for more arguments.
 * 
 * @author vincent
 *
 */
public class InsertPlayerArgs extends EntityKeyArg<Project> {
    private final Role role;
    
    public InsertPlayerArgs(final Project project, final String userEmail, final Role role) {
        super(project, userEmail);
        Preconditions.throwIfInvalidEmail(userEmail);
        Preconditions.throwIfNull("Must contain a valid role", role);
        Preconditions.throwIfEmptyString("Project key must not be empty", super.getEntity().getKey());
        this.role = role;
    }
    
    public String getProjectKey() {
        return super.getEntity().getKey();
    }

    public String getRole() {
        return role.name();
    }
}
