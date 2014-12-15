package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.UserConverters;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * Operations for User
 * 
 * @author aschneuw
 */
public final class UserOperations {
    
    /**
     * operation to login a user 
     */
    public static final ScrumToolOperation<String, ScrumUser> LOGIN_USER = 
            new ScrumToolOperation<String, ScrumUser>() {
        @Override
        public ScrumUser operation(String arg, Scrumtool service) throws IOException {
                return service.loginUser(arg).execute();
        }
    };

    /**
     * operation to update a user
     */
    public static final ScrumToolOperation<User, Void> UPDATE_USER = 
            new ScrumToolOperation<User, Void>() {
        @Override
        public Void operation(User arg, Scrumtool service) throws IOException {
            ScrumUser scrumUser = UserConverters.USER_TO_SCRUMUSER.convert(arg);
            return service.updateScrumUser(scrumUser).execute();
        }
    };
    
    /**
     * operation to delete a user
     */
    public static final ScrumToolOperation<String, Void> DELETE_USER = 
            new ScrumToolOperation<String, Void>() {
        @Override
        public Void operation(String arg, Scrumtool service) throws IOException {
                return service.removeScrumUser(arg).execute();
        }
    };
}