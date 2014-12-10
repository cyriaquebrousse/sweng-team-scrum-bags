package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.database.google.operations.UnauthenticatedOperation;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * 
 * @author aschneuw
 *
 */
public class UnauthenticatedOperationTest extends TestCase {

    private final Scrumtool testReturnNull =
            new Scrumtool(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null) {
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
    
    private final Scrumtool testThrowIoException =
            new Scrumtool(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null) {
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
        
    public void testNullOperation() {
        try {
            new UnauthenticatedOperation<Object, Object>(null, testReturnNull);
            fail("NullPointerException Expected for null operation argument");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullService() {
        try {
            new UnauthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS, null);
            fail("NullPointerException Expected for null operation argument");
        } catch (NullPointerException e) {
            
        }
        
    }
    
    public void testUnauthenticatedOperation() {
        try {
            UnauthenticatedOperation<Void, CollectionResponseScrumProject> op =
                    new UnauthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS,
                            testReturnNull);
            assertNull(op.execute(null));
            fail("NullResult expected");
        } catch (ScrumToolException e) {
            
        }
    }
    
    public void testUnauthenticatedOperationScrumToolException() {
        UnauthenticatedOperation<Void, CollectionResponseScrumProject> op =
                new UnauthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS,
                        testThrowIoException);
        try {
            op.execute(null);
            fail("ScrumToolException expected");
        } catch (ScrumToolException e) {
            //OK
        }
    }
    
    public void testGetOperation() {
        UnauthenticatedOperation<Void, CollectionResponseScrumProject> op =
                new UnauthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS,
                        testReturnNull);
        assertEquals(ProjectOperations.LOAD_PROJECTS, op.getOperation());
    }
}
