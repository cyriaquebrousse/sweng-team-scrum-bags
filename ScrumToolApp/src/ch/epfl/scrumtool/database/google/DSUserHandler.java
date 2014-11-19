package ch.epfl.scrumtool.database.google;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.conversion.OperationStatusConverters;
import ch.epfl.scrumtool.database.google.conversion.UserConverters;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.DSOperationExecutor;
import ch.epfl.scrumtool.database.google.operations.Operations;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.OperationStatus;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * 
 * @author aschneuw
 * 
 */
public class DSUserHandler implements UserHandler {
    @Override
    public void loginUser(final String email, final Callback<User> callback) {
        DSExecArgs.Factory<String, ScrumUser, User> builder = 
                new DSExecArgs.Factory<String, ScrumUser, User>(MODE.UNAUTHETICATED);
        builder.setCallback(callback);
        builder.setConverter(UserConverters.SCRUMUSER_TO_USER);
        builder.setOperation(Operations.LOGIN_USER);
        DSOperationExecutor.execute(email, builder.build());
    }

    @Override
    public void update(User modified, User ref, Callback<Boolean> callback) {
        DSExecArgs.Factory<User, OperationStatus, Boolean> builder =
                new DSExecArgs.Factory<User, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        builder.setOperation(Operations.UPDATE_USER);
        DSOperationExecutor.execute(modified, builder.build());
    }

    @Override
    public void remove(final User user, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, OperationStatus, Boolean> factory = 
                new DSExecArgs.Factory<String, OperationStatus, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(OperationStatusConverters.OPSTAT_TO_BOOLEAN);
        factory.setOperation(Operations.DELETE_USER);
        DSOperationExecutor.execute(user.getEmail(), factory.build());
    }
    
    @Override
    public void load(final String userKey, final Callback<User> callback) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void insert(User user, Callback<User> callback) {
        throw new UnsupportedOperationException();
    }
}
