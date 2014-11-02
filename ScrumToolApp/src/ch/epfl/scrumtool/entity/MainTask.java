/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.scrumtool.database.DoubleEntityDatabaseHandler;

/**
 * @author Vincent
 * 
 */
public class MainTask extends AbstractTask {

    private final Set<Issue> mIssues;
    private Priority mPriority;

    /**
     * @param name
     * @param description
     * @param status
     * @param subtasks
     * @param priority
     */
    public MainTask(long id, String name, String description, Status status,
            Set<Issue> issues, Priority priority) {
        super(id, name, description, status);
        if (issues == null || priority == null) {
            throw new NullPointerException("MainTask.Constructor");
        }
        this.mIssues = issues;
        this.mPriority = priority;

    }
    
    public void setIssue(DoubleEntityDatabaseHandler<MainTask, Issue> handler, Issue issue){
    	handler.insert(this, issue);
    }

    /**
     * @return the subtasks
     */
    public Set<Issue> getIssues() {
        return mIssues;
    }

    /**
     * @param issue
     *            the issue to add
     */
    public void addIssue(Issue issue) {
        if (issue != null) {
            this.mIssues.add(issue);
        }
    }

    /**
     * @param issue
     *            the issue to remove
     */
    public void removeIssue(Issue issue) {
        if (issue != null) {
            this.mIssues.remove(issue);
        }
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return mPriority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Priority priority) {
        if (priority != null) {
            this.mPriority = priority;
        }
    }

    public int getIssuesFinishedCount() {
        int count = 0;
        for (Issue i : mIssues) {
            if (i.getStatus() == Status.FINISHED) {
                ++count;
            }
        }
        return count;
    }
    
    public float getEstimatedTime() {
        float estimation = 0f;
        float issueEstimation;
        boolean estimated = true;
        for (Issue i : mIssues) {
            issueEstimation = i.getEstimatedTime();
            if (issueEstimation < 0) {
                estimated = false;
            } else {
                estimation += issueEstimation;
            }
        }
        return estimated ? estimation : -1;
    }
    
    public static class Builder {
        private long id;
        private String name;
        private String description;
        private Status status;
        private Set<Issue> issues;
        private Priority priority;

        public Builder() {
            this.issues = new HashSet<Issue>();
        }
        
        /**
         * 
         * @return the id
         */
        public long getId() {
        	return id;
        }
        
        public void setId(long id) {
        	this.id = id;
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
        public void setName(String name) {
            if (name != null) {
                this.name = name;
            }
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
        public void setDescription(String description) {
            if (description != null) {
                this.description = description;
            }
        }
        
        /**
         * 
         * @return the status
         */
        public Status getStatus() {
        	return this.status;
        }
        
        public void setStatus(Status status) {
        	if (status.isAValidStatus()) {
        		this.status = status;
        	}
        }

        /**
         * @return the issues
         */
        public Set<Issue> getIssues() {
            return issues;
        }
        
        public void setIssues(Set<Issue> issues) {
        	this.issues = issues;
        }
        
        public Priority getPriority() {
        	return priority;
        }
        
        public void setPriority(Priority priority) {
        	this.priority = priority;
        }
        
        public MainTask build() {
            return new MainTask(this.id, this.name, this.description, this.status, this.issues, this.priority);
        }
    }
}
