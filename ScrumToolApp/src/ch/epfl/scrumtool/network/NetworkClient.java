/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.ArrayList;

import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.User;

/**
 * @author AlexVeuthey
 *
 */
public class NetworkClient implements AbstractNetworkClient {
    @Override
    public ArrayList<MainTask> getBacklog() {
        return null;
    }
    
    @Override
    public ArrayList<User> getUsers() {
        return null;
    }
}
