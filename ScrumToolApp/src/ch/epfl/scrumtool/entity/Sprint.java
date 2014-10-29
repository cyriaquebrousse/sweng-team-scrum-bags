package ch.epfl.scrumtool.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio
 * @author Cyriaque Brousse
 */
public final class Sprint {
    
    private long deadline;
    private final Set<Issue> issues;

    /**
     * Constructs a new sprint
     * 
     * @param deadline
     *            the deadline in milliseconds. Must be non-negative
     * @param issues
     *            the initial set of issues
     * @see Date#getTime()
     */
    public Sprint(long deadline, Set<Issue> issues) {
        if (!deadlineIsValid(deadline) || issues == null) {
            throw new IllegalArgumentException("Deadline was invalid, or issue set was null");
        }
        this.deadline = deadline;
        this.issues = new HashSet<Issue>(issues);
    }
    
    /**
     * Constructs a new sprint from a Java date
     * 
     * @param date
     *            the deadline as a Java date
     * @param issues
     *            the set of issues
     */
    public Sprint(Date deadline, Set<Issue> issues) {
        this(deadline.getTime(), issues);
    }
    
    /**
     * Constructs a new sprint from a Java calendar
     * 
     * @param date
     *            the deadline as a Java calendar
     * @param issues
     *            the set of issues
     */
    public Sprint(Calendar deadline, Set<Issue> issues) {
        this(deadline.getTimeInMillis(), issues);
    }

    /**
     * Copy constructor
     * 
     * @param other
     *            the sprint to copy
     */
    public Sprint(Sprint other) {
        this(other.getDeadline(), other.getIssues());
    }

    /**
     * @return the deadline in milliseconds
     * @see Date#getTime()
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * @param deadline
     *            the deadline to set
     */
    public void setDeadline(long deadline) {
        if (!deadlineIsValid(deadline)) {
            throw new IllegalArgumentException("Invalid deadline");
        }
        this.deadline = deadline;
    }
    
    /**
     * @param deadline
     *            the deadline to set
     */
    public void setDeadline(Date deadline) {
        setDeadline(deadline.getTime());
    }
    
    /**
     * @param deadline
     *            the deadline to set
     */
    public void setDeadline(Calendar deadline) {
        setDeadline(deadline.getTimeInMillis());
    }

    /**
     * @return the set of issues
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) o;
        if (other.getDeadline() != this.getDeadline()) {
            return false;
        }
        if (!other.getIssues().equals(this.getIssues())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return (int) (new Date(getDeadline()).hashCode() + issues.hashCode()) % Integer.MAX_VALUE;
    }

    /**
     * @param deadline
     *            the deadline whose validity is to be checked
     * @return true if the deadline is valid, false otherwise
     */
    private boolean deadlineIsValid(long deadline) {
        return deadline >= 0;
    }

}
