/**
 * 
 */
package ch.epfl.scrumtool.database.google.conversion;

import java.util.Date;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * @author aschneuw
 *
 */
public final class Converters {
    public static final EntityConverter<ScrumUser, User> SCRUMUSER_TO_USER = 
            new EntityConverter<ScrumUser, User>() {
        
        @Override
        public User convert(ScrumUser b) {
            assert b != null;
            
            User.Builder builder = new User.Builder();

            String email = b.getEmail();
            if (email != null) {
                builder.setEmail(b.getEmail());
            }

            String name = b.getName();
            if (name != null) {
                builder.setName(name);
            }
            
            String lastName = b.getLastName();
            if (lastName != null) {
                builder.setLastName(lastName);
            }

            String companyName = b.getCompanyName();
            if (companyName != null) {
                builder.setCompanyName(companyName);
            }

            String jobTitle = b.getJobTitle();
            if (jobTitle != null) {
                builder.setJobTitle(jobTitle);
            }
                 
            Long dateOfBirth = b.getDateOfBirth();
            if (dateOfBirth != null) {
                Date date = new Date();
                date.setTime(dateOfBirth);
                builder.setDateOfBirth(date);
            }

            return builder.build();
        }
    };
    
    public static final EntityConverter<User, ScrumUser> USER_TO_SCRUMUSER = new EntityConverter<User, ScrumUser>() {
        
        @Override
        public ScrumUser convert(User user) {
            assert user != null;

            ScrumUser dbUser = new ScrumUser();

            dbUser.setCompanyName(user.getCompanyName());
            dbUser.setDateOfBirth(user.getDateOfBirth().getTime());
            dbUser.setJobTitle(user.getJobTitle());

            // Currently doesn't put timestamps to to ScrumUser

            dbUser.setEmail(user.getEmail());
            dbUser.setLastName(user.getLastName());
            dbUser.setName(user.getName());

            return dbUser;
        }
    };
    
    public static final EntityConverter<OperationStatus, Boolean> OPSTAT_TO_BOOLEAN = 
            new EntityConverter<OperationStatus, Boolean>() {
        
        @Override
        public Boolean convert(OperationStatus a) {
            return a.getSuccess();
        }
    };
    
    
}
