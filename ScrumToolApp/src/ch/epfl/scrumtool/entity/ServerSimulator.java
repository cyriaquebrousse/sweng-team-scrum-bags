/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author ketsio
 *
 */
public final class ServerSimulator {
    
    private final static List<UserInterface> users = Arrays.asList(
            Entity.createUser(0, "John Smith"),
            Entity.createUser(1, "Maria Linda"),
            Entity.createUser(2, "Cyriaque Brousse"),
            Entity.createUser(3, "Loris Leiva"),
            Entity.createUser(4, "Arjen Lenstra"),
            Entity.createUser(5, "Michael Scofield"));
    
    private final static List<ProjectInterface> projects = Arrays.asList(
            Entity.createProject("Cool Project",
                    new HashSet<>(Arrays.asList(Entity.JOHN_SMITH, Entity.MARIA_LINDA, Entity.CYRIAQUE_BROUSSE, Entity.CYRIAQUE_BROUSSE)),
                    new HashSet<>(Arrays.asList(Entity.TASK_A, Entity.TASK_B))),
            Entity.createProject("Super Project",
                    new HashSet<>(Arrays.asList(Entity.JOHN_SMITH, Entity.MARIA_LINDA, Entity.ARJEN_LENSTRA, Entity.MICHAEL_SCOFIELD)),
                    new HashSet<>(Arrays.asList(Entity.TASK_C, Entity.TASK_D))));

    public static UserInterface getUserById(int id) {
        return users.get(id);
    }
    
    public static ProjectInterface getProjectById(int id) {
        return projects.get(id);
    }
}
