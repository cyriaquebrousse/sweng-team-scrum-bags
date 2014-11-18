package ch.epfl.scrumtool.database.google;

import java.io.IOException;

import android.os.AsyncTask;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.conversion.Converters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.Operations;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.exception.NotAuthenticatedException;
import ch.epfl.scrumtool.network.GoogleSession;
import ch.epfl.scrumtool.network.Session;
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
    public void remove(final User user, final Callback<Boolean> callback) {
        AsyncTask<ScrumUser, Void, OperationStatus> task = new AsyncTask<ScrumUser, Void, OperationStatus>() {

            @Override
            protected OperationStatus doInBackground(ScrumUser... params) {
                OperationStatus opStat = null;
                try {
                    GoogleSession session = (GoogleSession) Session.getCurrentSession();
                    opStat = session.getAuthServiceObject().removeScrumUser(params[0].getEmail()).execute();
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
                    callback.failure("Error, could not delete user.");
                }
            }

        };
        ScrumUser tmpUser = new ScrumUser();
        tmpUser.setEmail(user.getEmail());
        
        task.execute(tmpUser);
    }
}
