package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Vincent
 * @author zenhaeus
 * @author aschneuw
 */
public final class MainTask extends AbstractTask implements Serializable, Comparable<MainTask> {

    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.TASK";
    private static final long serialVersionUID = 4279399766459657365L;
    
    
    private final int totalIssues;
    private final int finishedIssues;
    private final float totalIssueTime;
    private final float finishedIssueTime;

    /**
     * @return the totalIssues
     */
    public int getTotalIssues() {
        return totalIssues;
    }

    /**
     * @return the finishedIssues
     */
    public int getFinishedIssues() {
        return finishedIssues;
    }

    /**
     * @return the totalIssueTime
     */
    public float getTotalIssueTime() {
        return totalIssueTime;
    }

    /**
     * @return the finishedIssueTime
     */
    public float getFinishedIssueTime() {
        return finishedIssueTime;
    }
    
    public int unfinishedIssues() {
        return totalIssues - finishedIssues;
    }
    
    public float unfinishedIssueTime() {
        return totalIssueTime - finishedIssueTime;
    }

    /**
     * @param name
     * @param description
     * @param status
     * @param priority
     */
    
    private MainTask(Builder builder) {
        super(builder.id, builder.name, builder.description, builder.status, builder.priority);
        
        if (builder.finishedIssues < 0
                || builder.finishedIssueTime < 0
                || builder.totalIssues < 0 
                || builder.totalIssueTime < 0) {
            throw new IllegalArgumentException("Issue counter and times must be greater than 0");
        }
        
        if (builder.finishedIssues > builder.totalIssues) {
            throw new IllegalArgumentException("Number of completed issues can't be greater "
                        +"than the total number of issues");
        }
        if (builder.finishedIssueTime > builder.totalIssueTime) {
            throw new IllegalArgumentException("Total estimated time can't be greater "
                        +"than the total estimated time of finished issues");
        }
        
        this.finishedIssues = builder.finishedIssues;
        this.finishedIssueTime = builder.finishedIssueTime;
        this.totalIssues = builder.totalIssues;
        this.totalIssueTime = builder.totalIssueTime;
    }

    /**
     * Creates the maintask in the DS
     * 
     * @param project
     * @param callback
     */
    public void insert(final Project project, final Callback<MainTask> callback) {
        Client.getScrumClient().insertMainTask(this, project, callback);
    }

    /**
     * Updates the maintask in the DS
     * 
     * @param ref
     * @param project
     * @param callback
     */
    public void update(final MainTask ref, final Callback<Void> callback) {
        Client.getScrumClient().updateMainTask(this, ref, callback);
    }

    /**
     * Removes the maintask form the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Void> callback) {
        Client.getScrumClient().deleteMainTask(this, callback);
    }

    /**
     * Loads the issues of this maintask from the DS
     * 
     * @param callback
     */
    public void loadIssues(final Callback<List<Issue>> callback) {
        Client.getScrumClient().loadIssues(this, callback);
    }
    
    /**
     * Get new instance of Builder
     * @return
     */
    public Builder getBuilder() {
        return new Builder(this);
    }
    
    
    public float completedIssueRate() {
        if (totalIssues == 0) {
            return 0;
        } else {
            return finishedIssues / (float) totalIssues;
        }
    }
    
    public float issueTimeCompletionRate() {
        if (totalIssueTime == 0) {
            return 0;
        } else {
            return finishedIssueTime / totalIssueTime;
        }
        
    }

    /**
     * Builder class for the MainTask object
     * 
     * @author zenhaeus
     * 
     */
    public static class Builder {
        private String id;
        private String name;
        private String description;
        private Status status;
        private Priority priority;
        private int totalIssues;
        private int finishedIssues;
        private float totalIssueTime;
        private float finishedIssueTime;

        /**
         * @return the totalIssues
         */
        public int getTotalIssues() {
            return totalIssues;
        }

        /**
         * @param totalIssues the totalIssues to set
         */
        public Builder setTotalIssues(int totalIssues) {
            this.totalIssues = totalIssues;
            return this;
        }

        /**
         * @return the finishedIssues
         */
        public int getFinishedIssues() {
            return finishedIssues;
        }

        /**
         * @param finishedIssues the finishedIssues to set
         */
        public Builder setFinishedIssues(int finishedIssues) {
            this.finishedIssues = finishedIssues;
            return this;
        }

        /**
         * @return the totalIssueTime
         */
        public float getTotalIssueTime() {
            return totalIssueTime;
        }

        /**
         * @param totalIssueTime the totalIssueTime to set
         */
        public Builder setTotalIssueTime(float totalIssueTime) {
            this.totalIssueTime = totalIssueTime;
            return this;
        }

        /**
         * @return the finishedIssueTime
         */
        public float getFinishedIssueTime() {
            return finishedIssueTime;
        }

        /**
         * @param finishedIssueTime the finishedIssueTime to set
         */
        public Builder setFinishedIssueTime(float finishedIssueTime) {
            this.finishedIssueTime = finishedIssueTime;
            return this;
        }

        public Builder() {
            this.id = "";
            this.name = "";
            this.description = "";
            this.status = Status.READY_FOR_ESTIMATION;
            this.priority = Priority.NORMAL;
            this.finishedIssues = 0;
            this.finishedIssueTime = 0;
            this.totalIssues = 0;
            this.totalIssueTime = 0;
        }

        public Builder(MainTask task) {
            this.id = task.getKey();
            this.name = task.getName();
            this.description = task.getDescription();
            this.status = task.getStatus();
            this.priority = task.getPriority();
        }

        /**
         * 
         * @return the id
         */
        public String getKey() {
            return id;
        }

        public MainTask.Builder setKey(String key) {
            if (key != null) {
                this.id = key;
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
         *            the name to set
         */
        public MainTask.Builder setName(String name) {
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
        public MainTask.Builder setDescription(String description) {
            if (description != null) {
                this.description = description;
            }
            return this;
        }

        public Status getStatus() {
            return this.status;
        }

        public MainTask.Builder setStatus(Status status) {
            if (status != null) {
                this.status = status;
            }
            return this;
        }

        public Priority getPriority() {
            return priority;
        }

        public MainTask.Builder setPriority(Priority priority) {
            if (priority != null) {
                this.priority = priority;
            }
            return this;
        }

        public MainTask build() {
            return new MainTask(this);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MainTask)) {
            return false;
        }
        
        MainTask otherMainTask = (MainTask) o;
        
        return super.equals(otherMainTask)
                && this.finishedIssues == otherMainTask.finishedIssues
                && this.finishedIssueTime == otherMainTask.finishedIssueTime
                && this.totalIssues == otherMainTask.totalIssues
                && this.totalIssueTime == otherMainTask.totalIssueTime;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(MainTask that) {
        if (that == null) {
            return 1;
        }
        
        int comparison = this.getStatus().compareTo(that.getStatus());
        if (comparison != 0) {
            return comparison;
        }
        
        comparison = this.getPriority().compareTo(that.getPriority());
        if (comparison != 0) {
            return comparison;
        }
        
        return this.getName().compareTo(that.getName());
    }
    
}
