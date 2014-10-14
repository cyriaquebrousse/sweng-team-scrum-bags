/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.ArrayList;

/**
 * @author AlexVeuthey
 *
 */
public class NetworkClient implements AbstractNetworkClient {
    @Override
    public ArrayList<BogusTask> getBacklog() {
        return null;
    }
    
    @Override
    public ArrayList<BogusUser> getUsers() {
        return null;
    }
}
