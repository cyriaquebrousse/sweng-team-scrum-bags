/**
 * 
 */
package ch.epfl.scrumtool.network;

import java.util.ArrayList;

/**
 * @author AlexVeuthey
 *
 */
public interface AbstractNetworkClient {
    
    ArrayList<BogusTask> getBacklog();
    ArrayList<BogusUser> getUsers();
}