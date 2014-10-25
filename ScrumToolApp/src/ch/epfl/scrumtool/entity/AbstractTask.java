/**
 * 
 */
package ch.epfl.scrumtool.entity;

/**
 * @author Vincent
 * 
 */
public abstract class AbstractTask {
    private long mId;
    private String mName;
    private String mDescription;
    private Status mStatus;

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
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mStatus = status;
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
        return mName;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name != null) {
            this.mName = name;
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        if (description != null) {
            this.mDescription = description;
        }
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return mStatus;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        if (status != null) {
            this.mStatus = status;
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return mId;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.mId = id;
    }

}
