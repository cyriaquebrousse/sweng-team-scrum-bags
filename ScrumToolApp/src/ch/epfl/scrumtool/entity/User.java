package ch.epfl.scrumtool.entity;


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
    public void loadProjects(DatabaseHandler<Project> db, DatabaseCallback<Project> callback ){
    	db.load(this.name, callback );
    }

    public static class Builder {
        private String email;
        private String name;
        
        
        public Builder(){
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
