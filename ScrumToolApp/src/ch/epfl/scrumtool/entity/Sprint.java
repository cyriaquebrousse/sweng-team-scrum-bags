package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio, zenhaeus
 * @author Cyriaque Brousse
 */

public final class Sprint {
    private final String key;
    private final String title;
    private final Date deadline;

    /**
     * Constructs a new sprint
     * 
     * @param id
     *            the unique identifier
     * @param deadline
     *            the deadline in milliseconds. Must be non-negative
     * @see java.util.Date#getTime()
     */
    private Sprint(String id, String title, Date deadline) {
        this.key = id;
        this.title = title;
        this.deadline = deadline;
    }

    /**
     * @return the deadline in milliseconds
     * @see java.util.Date#getTime()
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * @return the id
     */
    public String getKey() {
        return key;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     */

    public static class Builder {

        private String keyb;
        private String title;

        private Date deadline;

        public Builder() {
            this.keyb = "";
            this.title = "";
            this.deadline = new Date();
        }

        /**
         * @param otherSprint
         */
        public Builder(Sprint otherSprint) {
            this.deadline = otherSprint.deadline;
            this.title = otherSprint.title;
            this.keyb = otherSprint.key;
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
        public Sprint.Builder setKey(String id) {
            this.keyb = id;
            return this;
        }

        /**
         * @return the deadLine
         */
        public Date getDeadline() {
            return deadline;
        }

        /**
         * @param deadline
         *            the deadline to set
         */
        public Sprint.Builder setDeadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Sprint.Builder setTitle(String newTitle) {
            this.title = newTitle;
            return this;
        }

        /**
         * @return
         */
        public Sprint build() {
            return new Sprint(this.keyb, this.title, this.deadline);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) o;
        return other.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
