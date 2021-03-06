package ch.epfl.scrumtool.entity;

import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.google.containers.TaskIssueProject;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * Represents a user
 * @author vincent
 * @author aschneuw
 * @author zenhaeus
 */
public final class User implements Serializable, Comparable<User> {
    /**
     * Gender enum
     * @author aschneuw
     */
    public enum Gender {MALE, FEMALE, UNKNOWN};

    private static final long serialVersionUID = 7681922700115023885L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.USER";

    private final String email;
    private final String name;
    private final String lastName;
    private final long dateOfBirth;
    private final String jobTitle;
    private final String companyName;
    private final Gender gender;
    
    private User(Builder builder) {
        throwIfNull("User constructor parameters cannot be null",
                builder.email,
                builder.name,
                builder.lastName,
                builder.dateOfBirth,
                builder.jobTitle,
                builder.companyName,
                builder.gender
        );
        
        Preconditions.throwIfInvalidEmail(builder.email);
        
        this.email = builder.email;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.jobTitle = builder.jobTitle;
        this.companyName = builder.companyName;
        this.gender = builder.gender;
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
        return name + " " + lastName;
    }

    /**
     * 
     * @return the birthday date
     */
    public long getDateOfBirth() {
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
    
    public Gender getGender() {
        return this.gender;
    }
    
    /**
     * Gets the issues for a specified user
     * 
     * @param callback
     */
    public void loadIssuesForUser(final Callback<List<TaskIssueProject>> callback) {
        Client.getScrumClient().loadIssuesForUser(this, callback);
    }
    
    /**
     * Loads the players invited to a project for the current user
     * 
     * @param callback
     */
    public void loadInvitedPlayers(final Callback<List<Player>> callback) {
        Client.getScrumClient().loadInvitedPlayers(callback);
    }

    /**
     * Updates the user on the DS
     * 
     * @param callback
     */
    public void update(final Callback<Void> callback) {
        Client.getScrumClient().updateUser(this, callback);
    }

    /**
     * Removes the user from the DS
     * 
     * @param callback
     */
    public void remove(Callback<Void> callback) {
        Client.getScrumClient().deleteUser(this, callback);
    }
    
    /**
     * Get new instance of Builder
     * @return
     */
    public Builder getBuilder() {
        return new Builder(this);
    }


    /**
     * Builder class for the User object
     * 
     * @author zenhaeus
     */
    public static class Builder {
        private String email;
        private String name;
        private String lastName;
        private String jobTitle;
        private long dateOfBirth;
        private String companyName;
        private Gender gender;

        public Builder() {
            this.email = "";
            this.name = "";
            this.lastName = "";
            this.companyName = "";
            this.jobTitle = "";
            this.dateOfBirth = 0;
            this.gender = Gender.UNKNOWN;
        }

        public Builder(User otherUser) {
            this.email = otherUser.email;
            this.name = otherUser.name;
            this.lastName = otherUser.lastName;
            this.jobTitle = otherUser.jobTitle;
            this.dateOfBirth = otherUser.dateOfBirth;
            this.companyName = otherUser.companyName;
            this.gender = otherUser.gender;
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
            if (email != null) {
                this.email = email;
            }
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
            if (name != null) {
                this.name = name;
            }
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
            if (lastName != null) {
                this.lastName = lastName;
            }
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
            if (jobTitle != null) {
                this.jobTitle = jobTitle;
            }
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
            if (companyName != null) {
                this.companyName = companyName;
            }
            return this;
        }

        /**
         * 
         * @return birthday Date
         */
        public long getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * 
         * @param dateOfBirth
         * @return current Builder instance
         */
        public User.Builder setDateOfBirth(long dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        
        /**
         * 
         * @return gender
         */
        public Gender getGender() {
            return this.gender;
        }
        
        /**
         * 
         * @param gender
         * @return
         */
        public User.Builder setGender(Gender gender) {
            if (gender != null) {
                this.gender = gender;
            }
            return this;
        }

        /**
         * @return the projects
         */

        public User build() {
            return new User(this);
        }

    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        
        return 
                other.getEmail().equals(this.getEmail())
                && other.companyName.equals(this.companyName)
                && other.dateOfBirth == this.dateOfBirth
                && other.gender == this.gender
                && other.jobTitle.equals(this.jobTitle)
                && other.lastName.equals(this.lastName)
                && other.name.equals(this.name);
    }

    /**
     * based on e-mail address
     */
    @Override
    public int hashCode() {
        return email.hashCode();
    }

    /**
     * Order: LastName->Name
     */
    @Override
    public int compareTo(User that) {
        if (that == null) {
            return 1;
        }
        
        int comparison = this.getLastName().compareTo(that.getLastName());
        if (comparison != 0) {
            return comparison;
        }
        
        return this.getName().compareTo(that.getName());
    }
}