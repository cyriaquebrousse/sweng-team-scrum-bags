package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */
public final class User implements DatabaseInteraction<User> {
    private final String email;
    private final String name;
    private final Set<String> projectKeys;

    /**
     * @param email
     * @param name
     * @param projects
     */
    private User(String email, String name, Set<String> projectKeys) {
        if (email == null || name == null || projectKeys == null) {
            throw new NullPointerException("User.Constructor");
        }

        this.email = email;
        this.name = name;
        this.projectKeys = new HashSet<String>(projectKeys);
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
    public List<String> getProjectKeys() {
        return new ArrayList<String>(projectKeys);
    }

    public static class Builder {
        private String email;
        private String name;
        private Set<String> projectKeys;

        public Builder() {
            this.projectKeys = new HashSet<String>();
        }

        public Builder(User otherUser) {
            this.email = otherUser.email;
            this.name = otherUser.name;
            this.projectKeys = new HashSet<String>(otherUser.projectKeys);
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
        public List<String> getProjectsKeys() {
            return new ArrayList<String>(projectKeys);
        }

        public void addProjectKey(String k) {
            projectKeys.add(k);
        }
        
        public void addProjects(Set<String> k) {
            projectKeys = k;
        }

        public void removeProjectKey(String k) {
            projectKeys.remove(k);
        }

        public User build() {
            return new User(this.email, this.name, this.projectKeys);
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
