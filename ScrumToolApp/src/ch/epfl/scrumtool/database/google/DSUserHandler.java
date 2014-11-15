package ch.epfl.scrumtool.database.google;

import java.io.IOException;
import java.util.Date;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSUserHandler implements UserHandler {

    @Override
    public void insert(User user, Callback<User> callback) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the User in the Datastore for the corresponding mailaddress
     * (creates one automatically if it doesn't exist)
     * 
     * @email user mail address
     * @cB callback
     */
    @Override
    public void loginUser(final String email, final Callback<User> callback) {
        AsyncTask<String, Void, ScrumUser> task = new AsyncTask<String, Void, ScrumUser>() {
            @Override
            protected ScrumUser doInBackground(String... params) {
                Scrumtool service = GoogleSession.getServiceObject();
                ScrumUser user = null;
                try {
                    user = service.loginUser(params[0]).execute();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return user;
            }

            @Override
            protected void onPostExecute(ScrumUser b) {
                User.Builder builder= new User.Builder();
                
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

                User user = builder.build();
                callback.interactionDone(user);
            }
        };
        task.execute(email);
    }

    @Override
    public void load(final String userKey, final Callback<User> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final User user, User ref, final Callback<Boolean> callback) {
        AsyncTask<ScrumUser, Void, OperationStatus> task = new AsyncTask<ScrumUser, Void, OperationStatus>() {
            @Override
            protected OperationStatus doInBackground(ScrumUser... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session
                            .getCurrentSession();
                    params[0].setLastModUser(session.getUser().getEmail());
                    opStat = session.getAuthServiceObject().updateScrumUser(params[0]).execute();
                } catch (IOException | NotAuthenticatedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return opStat;
            }

            @Override
            protected void onPostExecute(OperationStatus b) {
                if (b != null) {
                    callback.interactionDone(b.getSuccess());
                } else {
                    callback.failure("ERROR");
                }
            }
        };
        
        ScrumUser dbUser = new ScrumUser();
        dbUser.setCompanyName(user.getCompanyName());
        dbUser.setDateOfBirth(user.getDateOfBirth().getTime());
        dbUser.setJobTitle(user.getJobTitle());

        dbUser.setLastModDate((new Date()).getTime());

        dbUser.setEmail(user.getEmail());
        dbUser.setLastName(user.getLastName());
        dbUser.setName(user.getName());
        
        task.execute(dbUser);
    }

    /* (non-Javadoc)
     * @see ch.epfl.scrumtool.database.DatabaseHandler#remove(java.lang.Object, ch.epfl.scrumtool.database.Callback)
     */
    @Override
    public void remove(User object, Callback<Boolean> cB) {
        // TODO Auto-generated method stub
        
    }

}
