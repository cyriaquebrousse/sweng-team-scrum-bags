/**
 * 
 */
package ch.epfl.scrumtool.database.google.conversion;

import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

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

            return dbUser;
        }
    };
}
