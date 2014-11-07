package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;
import ch.epfl.scrumtool.database.google.DSUserHandler;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */

public final class User implements DatabaseInteraction<User>, Serializable {

    private static final long serialVersionUID = 7681922700115023885L;

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
    public void loadProjects(Callback<List<Project>> callback) {
        DSUserHandler db = new DSUserHandler();
        db.loadProjects(this.name, callback);
        // TODO : shouldn't it use the email? name are not unique
        // loris : I think so !
    }
    
    /**
     * This adds a project for this user
     * The callback will contain the key of the added Project
     * @param callback The callback function 
     */
    public void addProject(Project p, Callback<String> callback) {
        DSUserHandler db = new DSUserHandler();
        db.addProject(this.name, p, callback);
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
        handler.update(this, successCb);

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
            Callback<Boolean> successCb) {
        handler.remove(this, successCb);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return other.getEmail().equals(this.getEmail());
    }
    
    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
