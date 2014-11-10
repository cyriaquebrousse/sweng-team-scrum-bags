/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.entity.User;

/**
 * @author Arno
 *
 */
public interface ProjectManager {
    void loadProjects(User user, Callback<List<Project>> cB);
    void updateProject();
    void deleteProject();
    
    
    
    
}
