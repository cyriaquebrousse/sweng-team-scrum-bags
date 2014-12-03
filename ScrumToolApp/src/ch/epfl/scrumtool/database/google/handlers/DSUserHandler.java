package ch.epfl.scrumtool.database.google.handlers;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.UserHandler;
import ch.epfl.scrumtool.database.google.conversion.UserConverters;
import ch.epfl.scrumtool.database.google.conversion.VoidConverter;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs;
import ch.epfl.scrumtool.database.google.operations.DSExecArgs.Factory.MODE;
import ch.epfl.scrumtool.database.google.operations.OperationExecutor;
import ch.epfl.scrumtool.database.google.operations.UserOperations;
import ch.epfl.scrumtool.entity.User;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

/**
 * @author aschneuw
 */
public class DSUserHandler implements UserHandler {
    @Override
    public void loginUser(final String email, final Callback<User> callback) {
        DSExecArgs.Factory<String, ScrumUser, User> builder = 
                new DSExecArgs.Factory<String, ScrumUser, User>(MODE.UNAUTHETICATED);
        builder.setCallback(callback);
        builder.setConverter(UserConverters.SCRUMUSER_TO_USER);
        builder.setOperation(UserOperations.LOGIN_USER);
        OperationExecutor.execute(email, builder.build());
    }

    @Override
    public void update(final User modified, final User ref, final Callback<Boolean> callback) {
        DSExecArgs.Factory<User, Void, Boolean> builder =
                new DSExecArgs.Factory<User, Void, Boolean>(MODE.AUTHENTICATED);
        builder.setCallback(callback);
        builder.setConverter(VoidConverter.VOID_TO_BOOLEAN);
        builder.setOperation(UserOperations.UPDATE_USER);
        OperationExecutor.execute(modified, builder.build());
    }

    @Override
    public void remove(final User user, final Callback<Boolean> callback) {
        DSExecArgs.Factory<String, Void, Boolean> factory = 
                new DSExecArgs.Factory<String, Void, Boolean>(MODE.AUTHENTICATED);
        factory.setCallback(callback);
        factory.setConverter(VoidConverter.VOID_TO_BOOLEAN);
        factory.setOperation(UserOperations.DELETE_USER);
        OperationExecutor.execute(user.getEmail(), factory.build());
    }
    
    @Override
    public void load(final String userKey, final Callback<User> callback) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void insert(final User user, final Callback<User> callback) {
        throw new UnsupportedOperationException();
    }
}
