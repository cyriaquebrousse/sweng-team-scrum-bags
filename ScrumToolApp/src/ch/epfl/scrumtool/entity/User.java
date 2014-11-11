package ch.epfl.scrumtool.entity;

import java.io.Serializable;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */

public final class User implements Serializable {

    private static final long serialVersionUID = 7681922700115023885L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.USER";

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
     * Builder class for the User object
     * 
     * @author zenhaeus
     * 
     */

    public static class Builder {
        private String email;
        private String name;

        public Builder() {
            this.email = "";
            this.name = "";
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
