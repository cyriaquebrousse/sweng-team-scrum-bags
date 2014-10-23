/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.ArrayList;

import ch.epfl.scrumtool.entity.TaskInterface;
import ch.epfl.scrumtool.entity.UserInterface;

/**
 * @author AlexVeuthey
 *
 */
public interface AbstractNetworkClient {
    
    ArrayList<TaskInterface> getBacklog();
    ArrayList<UserInterface> getUsers();
}