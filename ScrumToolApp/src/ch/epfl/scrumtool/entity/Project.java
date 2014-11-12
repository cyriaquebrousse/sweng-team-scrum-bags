package ch.epfl.scrumtool.entity;

import java.io.Serializable;

import ch.epfl.scrumtool.exception.NotAPlayerOfThisProjectException;

/**
 * @author Vincent, zenhaeus
 */
public final class Project implements Serializable {

    private static final long serialVersionUID = -4181818270822077982L;
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.PROJECT";

    private final String key;
    private final String name;
    private final String description;

    private Project(String key, String name, String description) {
        super();
        if (key == null || name == null || description == null) {
            throw new NullPointerException("Project.Constructor");
        }

        // TODO check that admin in players
        this.key = key;
        this.name = name;
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the admin
     */
    public Player getAdmin() {
        // TODO query database to get admin
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    @Deprecated // TODO create a method in ScrumClient and delete this one.
    public int getChangesCount(User user) {
        return 0;
    }

    @Deprecated // TODO create a method in ScrumClient and delete this one.
    public Role getRoleFor(User user) throws NotAPlayerOfThisProjectException {
        return Role.DEVELOPER;
    }

    /**
     * Builder class for Project object
     * 
     * @author zenhaeus
     * 
     */
    public static class Builder {
        private String keyb;
        private String name;
        private String description;

        public Builder() {
            this.name = "";
            this.description = "";
        }

        public Builder(Project other) {
            this.keyb = other.key;
            this.name = other.name;
            this.description = other.description;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public Project.Builder setName(String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description
         *            the description to set
         */
        public Project.Builder setDescription(String description) {
            if (description != null) {
                this.description = description;
            }
            return this;
        }

        /**
         * @return the id
         */
        public String getKey() {
            return keyb;
        }

        /**
         * @param id
         *            the id to set
         */
        public Project.Builder setKey(String id) {
            this.keyb = id;
            return this;
        }

        public Project build() {
            return new Project(this.keyb, this.name, this.description);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Project)) {
            return false;
        }
        Project other = (Project) o;
        return other.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
