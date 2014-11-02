package ch.epfl.scrumtool.entity;

import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */
public final class User implements DatabaseInteraction<User> {
    private final String email;
    private final String name;

    /**
     * @param email
     * @param name
     * @param projects
     */
    private User(String email, String name) {
        if (email == null || name == null) {
            throw new NullPointerException("User.Constructor");
        }

        this.email = email;
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the projects
     */
    public void loadProjects(DatabaseHandler<Project> db, Callback<List<Project>> callback) {
        db.loadAll(this.name, callback);
    }

    /**
     * Builder class for the User object
     * 
     * @author zenhaeus
     * 
     */
    public static class Builder {
        private String email;
        private String name;

        public Builder() {
        }

        public Builder(User otherUser) {
            this.email = otherUser.email;
            this.name = otherUser.name;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the projects
         */

        public User build() {
            return new User(this.email, this.name);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.entity.DatabaseInteraction#updateDatabase(java.lang
     * .Object, ch.epfl.scrumtool.entity.DatabaseHandler)
     */
    @Override
    public void updateDatabase(DatabaseHandler<User> handler,
            Callback<Boolean> successCb) {
        handler.update(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.entity.DatabaseInteraction#deleteFromDatabase(ch.epfl
     * .scrumtool.entity.DatabaseHandler)
     */
    @Override
    public void deleteFromDatabase(DatabaseHandler<User> handler,
            Callback<Boolean> successCB) {
        handler.remove(this);
    }

}
