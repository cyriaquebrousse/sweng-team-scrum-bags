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
public class NetworkClient implements AbstractNetworkClient {
    @Override
    public ArrayList<TaskInterface> getBacklog() {
        return null;
    }
    
    @Override
    public ArrayList<UserInterface> getUsers() {
        return null;
    }
}
