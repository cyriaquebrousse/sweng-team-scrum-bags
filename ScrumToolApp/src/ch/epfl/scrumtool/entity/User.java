package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */

public final class User implements Serializable {

    private static final long serialVersionUID = 7681922700115023885L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.USER";

    private final String email;
    private final String name;
    private final String lastName;
    private final Date dateOfBirth;
    private final String jobTitle;
    private final String companyName;

    /**
     * @param email
     * @param name
     * @param projects
     */
    private User(String email, String name, String lastName, Date dateOfBirth,
            String jobTitle, String companyName) {
        if (email == null || name == null || lastName == null
                || dateOfBirth == null || jobTitle == null
                || companyName == null) {
            throw new NullPointerException("User.Constructor");
        }
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
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
     * 
     * @return family / last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * 
     * @return concatenation of name & family Name separated with a space
     */
    public String fullname() {
        return name +" "+ lastName;
    }
    
    /**
     * 
     * @return the birthday date
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    /**
     * 
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * 
     * @return job Title
     */
    public String getJobTitle() {
        return this.jobTitle;
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
        private String lastName;
        private String jobTitle;
        private Date dateOfBirth;
        private String companyName;

        public Builder() {
            this.email = "";
            this.name = "";
            this.lastName = "";
            this.companyName = "";
            this.jobTitle = "";
            this.dateOfBirth = new Date();
        }

        public Builder(User otherUser) {
            this.email = otherUser.email;
            this.name = otherUser.name;
            this.lastName = otherUser.lastName;
            this.jobTitle = otherUser.jobTitle;
            this.dateOfBirth = new Date();
            this.dateOfBirth.setTime(otherUser.dateOfBirth.getTime());
            this.companyName = otherUser.companyName;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email
         */
        public User.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         */
        public User.Builder setName(String name) {
            this.name = name;
            return this;
        }
        
        /**
         * 
         * @return
         */
        public String getLastName() {
            return lastName;
        }
        
        /**
         * 
         * @param lastName
         * @return current Builder instance
         */
        public User.Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        /**
         * 
         * @return jobTitle
         */
        public String getJobTitle() {
            return jobTitle;
        }
        
        /**
         * 
         * @param jobTitle
         * @return current Builder instance
         */
        public User.Builder setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }
        
        /**
         * 
         * @return company Name
         */
        public String getCompanyName() {
            return this.companyName;
        }
        
        /**
         * 
         * @param companyName
         * @return current Builder instance
         */
        public User.Builder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        
        /**
         * 
         * @return birthday Date
         */
        public Date getDateOfBirth() {
            Date newDate = new Date();
            newDate.setTime(this.dateOfBirth.getTime());
            return newDate;
        }
        
        /**
         * 
         * @param dateOfBirth
         * @return current Builder instance
         */
        public User.Builder setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = new Date();
            this.dateOfBirth.setTime(dateOfBirth.getTime());
            return this;
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

        /**
         * @return the projects
         */

        public User build() {
            return new User(this.email, this.name, this.lastName, this.dateOfBirth, this.jobTitle, this.companyName);
        }

    }


}
