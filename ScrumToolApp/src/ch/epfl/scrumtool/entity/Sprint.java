package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio, zenhaeus
 * @author Cyriaque Brousse
 */

public final class Sprint {
    private final String key;
    private final long deadline;

    /**
     * Constructs a new sprint
     * 
     * @param id
     *            the unique identifier
     * @param deadline
     *            the deadline in milliseconds. Must be non-negative
     * @see java.util.Date#getTime()
     */
    private Sprint(String id, long deadline) {
        if (id == null || !deadlineIsValid(deadline)) {
            throw new IllegalArgumentException(
                    "Deadline was invalid or no id was provided");
        }
        this.key = id;
        this.deadline = deadline;
    }

    /**
     * @return the deadline in milliseconds
     * @see java.util.Date#getTime()
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * @return the id
     */
    public String getKey() {
        return key;
    }

    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     */

    public static class Builder {

        private String keyb;
        private long deadline;
        private Set<Issue> issues;

        public Builder() {
            this.issues = new HashSet<Issue>();
        }

        /**
         * @param otherSprint
         */
        public Builder(Sprint otherSprint) {
            this.deadline = otherSprint.deadline;
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
        public void setKey(String id) {
            this.keyb = id;
        }

        /**
         * @return the issues
         */
        public Set<Issue> getIssues() {
            return new HashSet<>(this.issues);
        }

        /**
         * @param issue
         *            the issue to add
         */
        public void addIssue(Issue issue) {
            if (issue != null) {
                this.issues.add(issue);
            }
        }

        /**
         * @param issue
         *            the issue to remove
         */
        public void removeIssue(Issue issue) {
            if (issue != null) {
                this.issues.remove(issue);
            }
        }

        /**
         * @return the deadLine
         */
        public long getDeadline() {
            return deadline;
        }

        /**
         * @param deadline
         *            the deadline to set
         */
        public void setDeadline(long deadline) {
            if (deadlineIsValid(deadline)) {
                this.deadline = deadline;
            }
        }

        /**
         * @return
         */
        public Sprint build() {
            return new Sprint(this.keyb, this.deadline);
        }
    }

    /**
     * @param deadline
     *            the deadline whose validity is to be checked
     * @return true if the deadline is valid, false otherwise
     */
    private static boolean deadlineIsValid(long deadline) {
        return deadline >= 0;
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
