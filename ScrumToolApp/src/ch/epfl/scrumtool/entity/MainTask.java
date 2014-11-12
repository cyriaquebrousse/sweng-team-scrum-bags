package ch.epfl.scrumtool.entity;

import java.io.Serializable;

/**
 * @author Vincent, zenhaeus
 * 
 */

public final class MainTask extends AbstractTask implements Serializable {
    
    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.TASK";
    private static final long serialVersionUID = 4279399766459657365L;
    
    private Priority priority;

    /**
     * @param name
     * @param description
     * @param status
     * @param subtasks
     * @param priority
     */
    private MainTask(String id, String name, String description, Status status, Priority priority) {
        super(id, name, description, status);
        if (priority == null) {
            throw new NullPointerException("MainTask.Constructor");
        }
        this.priority = priority;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    public int getIssuesFinishedCount() {
        throw new UnsupportedOperationException("Not yet implemented");
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
            this.name = "";
            this.description = "";
            this.status = Status.READY_FOR_ESTIMATION;
            this.priority = Priority.NORMAL;
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

        public MainTask.Builder setKey(String id) {
            if (id != null) {
                this.id = id;
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
            this.status = status;
            return this;
        }

        public Priority getPriority() {
            return priority;
        }

        public MainTask.Builder setPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public MainTask build() {
            return new MainTask(this.id, this.name, this.description,
                    this.status, this.priority);
        }
    }
}
