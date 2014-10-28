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
public interface AbstractNetworkClient {
    
    ArrayList<MainTask> getBacklog();
    ArrayList<User> getUsers();
}