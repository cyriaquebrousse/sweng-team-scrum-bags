/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public abstract class AbstractTask {
    private long id;
    private String name;
    private String description;
    private Status status;

    /**
     * @param id
     * @param name
     * @param description
     */
    public AbstractTask(long id, String name, String description, Status status) {
        super();
        if (name == null || description == null || status == null) {
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
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        if (status != null) {
            this.status = status;
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractTask)) {
            return false;
        }
        AbstractTask other = (AbstractTask) o;
        if (!other.getDescription().equals(this.description)) {
            return false;
        }
        if (other.getId() != this.id) {
            return false;
        }
        if (!other.getName().equals(this.name)) {
            return false;
        }
        if (other.getStatus() != this.status) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return (int) (id
                + name.hashCode()
                + description.hashCode()
                + status.hashCode()) % Integer.MAX_VALUE;
    }
}
