import java.util.*;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<Task>();

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

    public ArrayList<Task> getTasks(){
        return this.tasks;
    }

    public void listTasks(){
        if (this.tasks.isEmpty()){
            System.out.println("Task list is empty.");
        }
        for (Task task : this.tasks){
            String status;
            if (task.isStatus() == true){
                status = "Completed";
            }
            else {
                status = "Not completed";
            }
            if (task.getDescription() != "Empty") {
                System.out.println((this.tasks.indexOf(task)+1) + ". " + task.getTitle()+"\nDescrpition: "+task.getDescription()+"\nCreation Date and Time: "+DateOps.formatDate(DateOps.secToDate(task.getCreationDate()))+"\nStatus: "+status+"\n");
            }
            else {
                System.out.println((this.tasks.indexOf(task)+1) + ". " + task.getTitle()+"\nCreation Date and Time: "+DateOps.formatDate(DateOps.secToDate(task.getCreationDate()))+"\nStatus: "+status+"\n");
            }
        }
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
