/**
 * 
 */
package ch.epfl.scrumtool.network;

/**
 * @author Arno
 *
 */
public final class Client {
    private static ScrumClient currentClient = null;
    
    public static void setScrumClient(ScrumClient client) {
        Client.currentClient = client;
    }
    
    public static ScrumClient getScrumClient() {
        return currentClient;
    }
    

}
