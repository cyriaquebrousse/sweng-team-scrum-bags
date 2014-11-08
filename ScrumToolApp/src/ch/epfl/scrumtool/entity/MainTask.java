package ch.epfl.scrumtool.entity;

import java.io.Serializable;
import java.util.List;

import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.network.Client;

/**
 * @author Vincent, zenhaeus
 * 
 */

public final class MainTask extends AbstractTask implements Serializable {
    
    private static final long serialVersionUID = 4279399766459657365L;
    
    private Priority priority;

    /**
     * @param name
     * @param description
     * @param status
     * @param subtasks
     * @param priority
     */
    private MainTask(String id, String name, String description, Status status,
            Priority priority) {
        super(id, name, description, status);
        if (priority == null) {
            throw new NullPointerException("MainTask.Constructor");
        }
        this.priority = priority;
    }


    /**
     * @return the subtasks
     */
    public void loadIssues(Callback<List<Issue>> callback) {
        Client.getScrumClient().loadIssues(this, callback);
    }

    // TODO save and remove methods for issues

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    public int getIssuesFinishedCount() {
        int count = 0;
        // for (Issue i : issues) {
        // if (i.getStatus() == Status.FINISHED) {
        // ++count;
        // }
        // }
        return count;
    }

    public float getEstimatedTime() {
        float estimation = 0f;
        // float issueEstimation;
        boolean estimated = true;
        // for (Issue i : issues) {
        // issueEstimation = i.getEstimatedTime();
        // if (issueEstimation < 0) {
        // estimated = false;
        // } else {
        // estimation += issueEstimation;
        // }
        // }
        return estimated ? estimation : -1;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MainTask && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

        public Builder() {
            
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
        public String getId() {
            return id;
        }

        public void setId(String id) {
            if (id != null) {
                this.id = id;
            }
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

        public Priority getPriority() {
            return priority;
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public MainTask build() {
            return new MainTask(this.id, this.name, this.description,
                    this.status, this.priority);
        }
    }
}
