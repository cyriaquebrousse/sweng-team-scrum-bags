package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public abstract class AbstractTask {
    private String id;
    private String name;
    private String description;
    private Status status;

    /**
     * @param id
     * @param name
     * @param description
     */
    public AbstractTask(String id, String name, String description, Status status) {
        super();
        if (id == null || name == null || description == null || status == null) {
            throw new NullPointerException("AbstractTask.Constructor");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    /**
     * 
     * @return
     */
    public abstract float getEstimatedTime();

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

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AbstractTask)) {
            return false;
        }
        AbstractTask other = (AbstractTask) o;
        return other.id.equals(this.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
