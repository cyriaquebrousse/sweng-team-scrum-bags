package ch.epfl.scrumtool.gui.utils.test;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import ch.epfl.scrumtool.server.scrumtool.Scrumtool;

/**
 * 
 * @author aschneuw
 *
 */
public class MockScrumTool extends Scrumtool {

    public MockScrumTool(HttpTransport transport, JsonFactory jsonFactory,
            HttpRequestInitializer httpRequestInitializer) {
        super(transport, jsonFactory, httpRequestInitializer);
    }


}
