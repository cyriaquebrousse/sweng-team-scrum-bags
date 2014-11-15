package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.conversion.Converters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Builder;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.Operations;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.network.GoogleSession;
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

//    /**
//     * Returns the User in the Datastore for the corresponding mailaddress
//     * (creates one automatically if it doesn't exist)
//     * 
//     * @email user mail address
//     * @cB callback
//     */
//    @Override
//    public void loginUser(final String email, final Callback<User> callback) {
//        AsyncTask<String, Void, ScrumUser> task = new AsyncTask<String, Void, ScrumUser>() {
//            @Override
//            protected ScrumUser doInBackground(String... params) {
//                Scrumtool service = GoogleSession.getServiceObject();
//                ScrumUser user = null;
//                try {
//                    user = service.loginUser(params[0]).execute();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                return user;
//            }
//
//            @Override
//            protected void onPostExecute(ScrumUser scrumUser) {
//                User.Builder userBuilder = new User.Builder();
//                userBuilder.setName(scrumUser.getName());
//                userBuilder.setEmail(scrumUser.getEmail());
//                User user = userBuilder.build();
//                callback.interactionDone(user);
//            }
//        };
//        task.execute(email);
//    }
    
    @Override
    public void loginUser(final String email, final Callback<User> callback) {
        DSExecArgs.Builder<String, ScrumUser, User> builder = 
                new DSExecArgs.Builder<String, ScrumUser, User>();
        
        builder.setCallback(callback);
        builder.setConverter(Converters.SCRUMUSER_TO_USER);
        builder.setOperation(Operations.LOGIN_USER);
        
        DSOperationExecutor.execute(email, builder.build());
        
    }

    @Override
    public void load(final String userKey, final Callback<User> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User modified, User ref, Callback<Boolean> callback) {
        DSExecArgs.Builder<User, OperationStatus, Boolean> builder =
                new DSExecArgs.Builder<User, OperationStatus, Boolean>();
        builder.setCallback(callback);
        builder.setConverter(Converters.OPSTAT_TO_BOOLEAN);
        builder.setOperation(Operations.UPDATE_USER);
        DSOperationExecutor.execute(modified, builder.build());
    }

    @Override
    public void remove(User object, Callback<Boolean> cB) {
        throw new UnsupportedOperationException();
    }

}
