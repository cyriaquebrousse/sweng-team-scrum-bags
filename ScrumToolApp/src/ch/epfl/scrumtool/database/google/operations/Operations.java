/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.Converters;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * @author aschneuw
 *
 */
public final class Operations {
    public static final DatastoreOperation<String, ScrumUser> LOGIN_USER = 
            new DatastoreOperation<String, ScrumUser>() {

        @Override
        public ScrumUser execute(String a) throws IOException {
            Scrumtool service = GoogleSession.getServiceObject();
            return service.loginUser(a).execute();
        }
    };
    
    public static final DatastoreOperation<User, OperationStatus> UPDATE_USER = 
            new DatastoreOperation<User, OperationStatus>() {
        
        @Override
        public OperationStatus execute(User user) throws IOException {
            ScrumUser scrumUser = Converters.USER_TO_SCRUMUSER.convert(user);
            OperationStatus opStatus = null;
            try {
                GoogleSession session = (GoogleSession) Session.getCurrentSession();
                opStatus = session.getAuthServiceObject().updateScrumUser(scrumUser).execute();
            } catch (NotAuthenticatedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return opStatus;
        }
    };
}
