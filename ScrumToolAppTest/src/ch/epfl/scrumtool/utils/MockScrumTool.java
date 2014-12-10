package ch.epfl.scrumtool.utils;

import java.io.IOException;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;

public class MockScrumTool extends Scrumtool{

    public MockScrumTool(HttpTransport transport, JsonFactory jsonFactory,
            HttpRequestInitializer httpRequestInitializer) {
        super(transport, jsonFactory, httpRequestInitializer);
    }
    
    public class Test2 extends LoadInvitedPlayers {
        @Override
        public CollectionResponseScrumPlayer execute() throws IOException {
            // TODO Auto-generated method stub
            return super.execute();
        }
        
    }
    

}
