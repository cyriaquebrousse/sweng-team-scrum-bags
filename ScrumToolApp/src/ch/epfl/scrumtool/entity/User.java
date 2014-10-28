package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */
public class User implements DatabaseInteraction<User> {
    private final String mEmail;
    private final String mName;
    private final Set<String> mProjectKeys;

    /**
     * @param email
     * @param name
     * @param projects
     */
    private User(String email, String name, Set<String> projectKeys) {
        if (email == null || name == null || projectKeys == null) {
            throw new NullPointerException("User.Constructor");
        }

        this.mEmail = email;
        this.mName = name;
        this.mProjectKeys = new HashSet<String>(projectKeys);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * @return the projects
     */
    public List<String> getProjectKeys() {
        return new ArrayList<String>(mProjectKeys);
    }

    public static class Builder {
        private String mEmail;
        private String mName;
        private Set<String> mProjectKeys;

        public Builder() {
            this.mProjectKeys = new HashSet<String>();
        }

        public Builder(User otherUser) {
            this.mEmail = otherUser.mEmail;
            this.mName = otherUser.mName;
            this.mProjectKeys = new HashSet<String>(otherUser.mProjectKeys);
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        /**
         * @return the name
         */
        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        /**
         * @return the projects
         */
        public List<String> getProjectsKeys() {
            return new ArrayList<String>(mProjectKeys);
        }

        public void addProjectKey(String k) {
            mProjectKeys.add(k);
        }
        
        public void addProjects(Set<String> k) {
            mProjectKeys = k;
        }

        public void removeProjectKey(String k) {
            mProjectKeys.remove(k);
        }

        public User build() {
            return new User(this.mEmail, this.mName, this.mProjectKeys);
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
    public void updateDatabase(User reference, DatabaseHandler<User> handler) {
        handler.update(reference);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.scrumtool.entity.DatabaseInteraction#deleteFromDatabase(ch.epfl
     * .scrumtool.entity.DatabaseHandler)
     */
    @Override
    public void deleteFromDatabase(DatabaseHandler<User> handler) {
        handler.remove(this);
    }

}
