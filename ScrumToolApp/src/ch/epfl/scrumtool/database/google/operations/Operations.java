/**
 * 
 */
package ch.epfl.scrumtool.database.google.operations;

import java.io.IOException;

import ch.epfl.scrumtool.database.google.conversion.Converters;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * @author aschneuw
 *
 */
public final class Operations {
    public static final ScrumToolOperation<String, ScrumUser> LOGIN_USER = 
            new ScrumToolOperation<String, ScrumUser>() {

        @Override
        public ScrumUser execute(String arg, Scrumtool service) throws IOException {
            return service.loginUser(arg).execute();
        }
    };

    public static final ScrumToolOperation<User, OperationStatus> UPDATE_USER = 
            new ScrumToolOperation<User, OperationStatus>() {

        @Override
        public OperationStatus execute(User arg, Scrumtool service)
                throws IOException {
            ScrumUser scrumUser = Converters.USER_TO_SCRUMUSER.convert(arg);
            return service.updateScrumUser(scrumUser).execute();
        }
    };
}
