import java.util.*;

public class TaskManager {
    private LinkedList<Task> tasks = new LinkedList<Task>();

    public void addTask(String title, String description){
        Task task = new Task(title,description);
        this.tasks.add(task);
    }

    public boolean removeTask(String order){
        if ((Integer.valueOf(order)) > this.tasks.size()) {
            return false;
        }
        else {
            this.tasks.remove(Integer.valueOf(order)-1);
            return true;
        }
    }

    public void clearTaskList(){
        this.tasks.clear();
    }

    public LinkedList<Task> getTasks(){
        return this.tasks;
    }

    public boolean toggleTaskStatus(String order){
        if ((Integer.valueOf(order))>this.tasks.size()) {
            return false;
        }
        else {
            Task task = this.tasks.get(Integer.valueOf(order)-1);
            if (!task.isStatus()) {
                task.setStatus(true);
            } else if (task.isStatus()) {
                task.setStatus(false);
            }
            return true;
        }
    }
}
