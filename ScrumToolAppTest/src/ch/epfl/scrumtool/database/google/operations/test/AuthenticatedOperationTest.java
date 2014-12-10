package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.operations.AuthenticatedOperation;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.test.TestConstants;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * 
 * @author aschneuw
 *
 */
public class AuthenticatedOperationTest extends TestCase {
    
    private final Scrumtool testReturnNull = new Scrumtool(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null) {
        @Override
        public LoadProjects loadProjects(String userKey) throws IOException {
            LoadProjects loadProjects = new Scrumtool.LoadProjects("") {
                @Override
                public CollectionResponseScrumProject execute()
                        throws IOException {
                    return null;
                }
            };
            return loadProjects;
        }
    };
    
    private final Scrumtool testThrowIoException = new Scrumtool(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null) {
        @Override
        public LoadProjects loadProjects(String userKey) throws IOException {
            LoadProjects loadProjects = new Scrumtool.LoadProjects("") {
                @Override
                public CollectionResponseScrumProject execute()
                        throws IOException {
                    throw new IOException("MockIOException");
                }
            };
            return loadProjects;
        }
    };
    
    public void setUp() {
        
        
        
    }
    
    public void testNullOperation() {
        try {
            new AuthenticatedOperation<Object, Object>(null);
            fail("NullPointerException Expected for null operation argument");
        } catch (NullPointerException e) {
            
        }new GoogleSession(TestConstants.generateBasicUser(), testReturnNull);
    }
    
    public void testAuthenticatedOperation() {
        try {
            
            
            AuthenticatedOperation<Void, CollectionResponseScrumProject> op =
                    new AuthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS);
            assertNull(op.execute(null));
        } catch (ScrumToolException e) {
            fail("NullResult expected");
        }
    }
    
    public void testAuthenticatedOperationScrumToolException() {
        
        
    }
    
    public void testExecuteNullSession() {
        fail("Not yet implemented");
    }
    
    public void testExecute() {
        fail("Not yet implemented");

    }



}
