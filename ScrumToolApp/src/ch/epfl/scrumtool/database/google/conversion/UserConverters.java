/**
 * 
 */
package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Ensures convertion betwen ScrumUser and User
 * 
 * @author aschneuw
 * 
 */
public final class UserConverters {
    public static final EntityConverter<ScrumUser, User> SCRUMUSER_TO_USER = new EntityConverter<ScrumUser, User>() {

        @Override
        public User convert(ScrumUser dbUser) {
            assert dbUser != null;

            Preconditions.throwIfNull("Trying to convert a User with null parameters",
                    dbUser.getEmail(), dbUser.getName());
            
            User.Builder builder = new User.Builder();

            String email = dbUser.getEmail();
            if (email != null) {
                builder.setEmail(dbUser.getEmail());
            }

            String name = dbUser.getName();
            if (name != null) {
                builder.setName(name);
            }

            String lastName = dbUser.getLastName();
            if (lastName != null) {
                builder.setLastName(lastName);
            }

            String companyName = dbUser.getCompanyName();
            if (companyName != null) {
                builder.setCompanyName(companyName);
            }

            String jobTitle = dbUser.getJobTitle();
            if (jobTitle != null) {
                builder.setJobTitle(jobTitle);
            }

            Long dateOfBirth = dbUser.getDateOfBirth();
            if (dateOfBirth != null) {
                builder.setDateOfBirth(dateOfBirth);
            }
            
            String gender = dbUser.getGender();
            if (gender != null) {
                builder.setGender(Gender.valueOf(gender));
            } else {
                builder.setGender(Gender.UNKNOWN);
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
            dbUser.setDateOfBirth(user.getDateOfBirth());
            dbUser.setJobTitle(user.getJobTitle());

            // Currently we don't need LastModDate and LastModUser

            dbUser.setEmail(user.getEmail());
            dbUser.setLastName(user.getLastName());
            dbUser.setName(user.getName());
            if (user.getGender() != Gender.UNKNOWN) {
                dbUser.setGender(user.getGender().name());
            }

            return dbUser;
        }
    };
}
