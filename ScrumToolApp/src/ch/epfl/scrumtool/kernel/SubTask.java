/**
 * 
 */
package kernel;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Vincent
 *
 */
public class SubTask extends AbstractTask {
    
    private Date dueDate;
    private Player programer;
    private MainTask parent;

    /**
     * @param aName
     * @param aDescription
     */
    public SubTask(String aName, String aDescription, Date doUntil, Player aProgrammer, MainTask task) {
        super(aName, aDescription);
        dueDate = doUntil;
        programer = aProgrammer;
        parent = task;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the programer
     */
    public Player getProgramer() {
        return programer;
    }

    /**
     * @param programer the programer to set
     */
    public void setProgramer(Player programer) {
        this.programer = programer;
    }

    /**
     * @return the parent
     */
    public MainTask getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(MainTask parent) {
        this.parent = parent;
    }
    

}
