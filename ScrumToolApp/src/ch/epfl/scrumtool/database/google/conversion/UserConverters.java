package ch.epfl.scrumtool.database.google.conversion;

import static ch.epfl.scrumtool.util.Assertions.assertTrue;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.entity.User.Gender;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Ensures convertion betwen ScrumUser and User
 * 
 * @author aschneuw
 */
public final class UserConverters {
    public static final EntityConverter<ScrumUser, User> SCRUMUSER_TO_USER = new EntityConverter<ScrumUser, User>() {

        @Override
        public User convert(ScrumUser dbUser) {
            assertTrue(dbUser != null);
            throwIfNull("Trying to convert a User with null parameters",
                    dbUser.getEmail());

            Preconditions.throwIfInvalidEmail(dbUser.getEmail());

            User.Builder builder = new User.Builder();

            String email = dbUser.getEmail();
            builder.setEmail(email);

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
            ScrumUser dbUser = new ScrumUser();

            dbUser.setCompanyName(user.getCompanyName());
            dbUser.setDateOfBirth(user.getDateOfBirth());
            dbUser.setJobTitle(user.getJobTitle());
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
