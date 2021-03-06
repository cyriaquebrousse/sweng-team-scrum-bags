package ch.epfl.scrumtool.entity;

import static ch.epfl.scrumtool.util.Preconditions.throwIfEmptyString;
import static ch.epfl.scrumtool.util.Preconditions.throwIfNull;

import java.io.Serializable;

/**
 * Basic task object
 * @author Vincent
 */
public abstract class AbstractTask implements Serializable {
    private static final long serialVersionUID = 1197442142563029748L;
    
    private String key;
    private String name;
    private String description;
    private Status status;
    private Priority priority;

    /**
     * @param key
     * @param name
     * @param description
     */
    public AbstractTask(String key, String name, String description, Status status, Priority priority) {
        throwIfNull("Abstract task constructor parameters cannot be null",
                key, name, description, status, priority);
        throwIfEmptyString("Task name must not be empty", name);
        throwIfEmptyString("Task description must not be null", description);
        
        this.key = key;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
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
        return description;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the id
     */

    public String getKey() {
        return key;
    }
    
    /**
     * @return the priority
     */
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AbstractTask)) {
            return false;
        }
        AbstractTask other = (AbstractTask) o;
        return this.key.equals(other.key)
                && this.description.equals(other.description)
                && this.name.equals(other.name)
                && this.priority == other.priority
                && this.status == other.status;
    }
    
    /**
     * HashCode based on the the key
     */
    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
