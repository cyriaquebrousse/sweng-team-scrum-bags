package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.database.DatabaseHandler;
import ch.epfl.scrumtool.database.DatabaseInteraction;

/**
 * @author ketsio, zenhaeus
 */
public final class Sprint implements DatabaseInteraction<Sprint> {
    private final String id;
    private final Date deadLine;
    private final Set<Issue> issues;

    /**
     * @param deadLine
     * @param issues
     */
    private Sprint(String id, Date deadLine, Set<Issue> issues) {
        super();
        if (id == null || deadLine == null || issues == null) {
            throw new NullPointerException("Sprint.Constructor");
        }
        this.id = id;
        this.deadLine = new Date(deadLine.getTime());
        this.issues = new HashSet<Issue>(issues);
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the mDeadLine
     */
    public Date getDeadLine() {
        return new Date(deadLine.getTime());
    }


    /**
     * @return the mIssues
     */
    public Set<Issue> getIssues() {
        return issues;
    }

    
    /**
     * Builder class for the Sprint object
     * 
     * @author zenhaeus
     *
     */
    public static class Builder {
        private String id;
        private Date deadLine;
        private Set<Issue> issues;
        
        public Builder() {
            this.issues = new HashSet<Issue>();
        }
        
        public Builder(Sprint otherSprint) {
            this.deadLine = otherSprint.deadLine;
            this.issues = new HashSet<Issue>(otherSprint.issues);
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         *            the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the mIssues
         */
        public Set<Issue> getIssues() {
            return issues;
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
         * @return the mDeadLine
         */
        public Date getDeadLine() {
            return new Date(deadLine.getTime());
        }

        /**
         * @param deadLine
         *            the mDeadLine to set
         */
        public void setDeadLine(Date deadLine) {
            if (deadLine != null) {
                this.deadLine = new Date(deadLine.getTime());
            }
        }
        
        public Sprint build() {
            return new Sprint(this.id, this.deadLine, this.issues);
        }
    }


    @Override
    public void updateDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteFromDatabase(DatabaseHandler<Sprint> handler,
            Callback<Boolean> successCb) {
        // TODO Auto-generated method stub
        
    }
}
