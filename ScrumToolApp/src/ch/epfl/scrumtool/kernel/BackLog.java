/**
 * 
 */
package ch.epfl.scrumtool.kernel;

import java.util.ArrayList;

/**
 * @author Vincent
 * 
 */
public class BackLog {
    private ArrayList<MainTask> tasks;
    private Project project;

    public BackLog(Project aProject, ArrayList<MainTask> someTasks) {
        project = aProject;
        tasks = new ArrayList<MainTask>();
        for (MainTask t : someTasks){
            tasks.add(t);
        }
    }

    public void addTask(MainTask task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }else {
            throw new IndexOutOfBoundsException("BackLog.removeTask : index not in the range of the list");
        }
    }

    public Project getProject() {
        return project;
    }

}
