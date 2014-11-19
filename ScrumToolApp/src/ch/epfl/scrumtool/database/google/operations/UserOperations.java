/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.UserConverters;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.DeleteException;
import ch.epfl.scrumtool.exception.LoginException;
import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.exception.UpdateException;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * Operations for User
 * 
 * @author aschneuw
 *
 */
public final class UserOperations {
    
    public static final ScrumToolOperation<String, ScrumUser> LOGIN_USER = 
            new ScrumToolOperation<String, ScrumUser>() {
        @Override
        public ScrumUser execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.loginUser(arg).execute();
            } catch (IOException e) {
                throw new LoginException(e, "Login failed");
            }
        }
    };

    public static final ScrumToolOperation<User, OperationStatus> UPDATE_USER = 
            new ScrumToolOperation<User, OperationStatus>() {
        @Override
        public OperationStatus execute(User arg, Scrumtool service) throws ScrumToolException {
            ScrumUser scrumUser = UserConverters.USER_TO_SCRUMUSER.convert(arg);
            try {
                return service.updateScrumUser(scrumUser).execute();
            } catch (IOException e) {
                throw new UpdateException(e, "User update failed");
            }
        }
    };
    
    
    public static final ScrumToolOperation<String, OperationStatus> DELETE_USER = 
            new ScrumToolOperation<String, OperationStatus>() {
        @Override
        public OperationStatus execute(String arg, Scrumtool service) throws ScrumToolException {
            try {
                return service.removeScrumUser(arg).execute();
            } catch (IOException e) {
                throw new DeleteException(e, "User deletion failed");
            }
        }
    };
}
