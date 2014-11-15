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

    public static final String SERIALIZABLE_NAME = "ch.epfl.scrumtool.TASK";
    private static final long serialVersionUID = 4279399766459657365L;

    /**
     * @param name
     * @param description
     * @param status
     * @param subtasks
     * @param priority
     */
    private MainTask(String id, String name, String description, Status status,
            Priority priority) {
        super(id, name, description, status, priority);
        if (priority == null) {
            throw new NullPointerException("MainTask.Constructor");
        }
        // this.priority = priority;
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
    public void update(final MainTask ref, final Project project,
            final Callback<Boolean> callback) {
        Client.getScrumClient().updateMainTask(this, ref, project, callback);
    }

    /**
     * Removes the maintask form the DS
     * 
     * @param callback
     */
    public void remove(final Callback<Boolean> callback) {
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
            this.id = "";
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
