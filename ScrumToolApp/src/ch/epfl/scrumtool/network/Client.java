package ch.epfl.scrumtool.network;

/**
 * Represents a Client for the database operations
 * 
 * @author aschneuw
 */
public final class Client {
    private static ScrumClient currentClient = null;

    /**
     * @param client
     */
    public static void setScrumClient(ScrumClient client) {
        Client.currentClient = client;
    }

    /**
     * @return the current client
     */
    public static ScrumClient getScrumClient() {
        return currentClient;
    }
}