/**
 * 
 */
package kernel;

import java.util.ArrayList;

/**
 * @author Vincent
 *
 */
public class MainTask extends AbstractTask {
    
    private ArrayList<SubTask> subtasks;

    /**
     * @param aName
     * @param aDescription
     */
    public MainTask(String aName, String aDescription, ArrayList<SubTask> tasks) {
        super(aName, aDescription);
    }
    
    public void addSubTask(SubTask task){
        subtasks.add(task);
    }
    
    public void removeSubTask(int index){
        subtasks.remove(index);
    }

}
