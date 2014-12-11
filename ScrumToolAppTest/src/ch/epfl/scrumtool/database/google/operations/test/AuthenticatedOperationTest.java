package ch.epfl.scrumtool.database.google.operations.test;

import java.io.IOException;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.operations.AuthenticatedOperation;
import ch.epfl.scrumtool.database.google.operations.ProjectOperations;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * 
 * @author aschneuw
 *
 */
public class AuthenticatedOperationTest extends TestCase {
    
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
            new AuthenticatedOperation<Object, Object>(null);
            fail("NullPointerException Expected for null operation argument");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testAuthenticatedOperation() {
        try {
            new GoogleSession(ServerClientEntities.generateBasicUser(), testReturnNull);
            AuthenticatedOperation<Void, CollectionResponseScrumProject> op =
                    new AuthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS);
            assertNull(op.execute(null));
        } catch (ScrumToolException e) {
            fail("NullResult expected");
        }
    }
    
    public void testAuthenticatedOperationScrumToolException() {
        new GoogleSession(ServerClientEntities.generateBasicUser(), testThrowIoException);
        AuthenticatedOperation<Void, CollectionResponseScrumProject> op =
                new AuthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS);
        Session.destroySession();
        try {
            op.execute(null);
            fail("ScrumToolException expected");
        } catch (ScrumToolException e) {
            //OK
        }
    }
    
    public void testExecuteNullSession() {
        try {
            AuthenticatedOperation<Void, CollectionResponseScrumProject> op =
                    new AuthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS);
            op.execute(null);
            fail("ScrumToolException expected");
        } catch (NotAuthenticatedException e) {
            //OK
        } catch (ScrumToolException e) {
            fail("NotAuthenticatedException expected");
        }
    }
    
    public void testGetOperation() {
        AuthenticatedOperation<Void, CollectionResponseScrumProject> op =
                new AuthenticatedOperation<Void, CollectionResponseScrumProject>(ProjectOperations.LOAD_PROJECTS);
        assertEquals(ProjectOperations.LOAD_PROJECTS, op.getOperation());
    }
}