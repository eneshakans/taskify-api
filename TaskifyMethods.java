import java.util.Scanner;

public class TaskifyMethods {
    public static void addTask(Scanner scanner, TaskManager taskManager){
        scanner.nextLine();
        System.out.println("Enter a task title:");
        String taskTitle = scanner.nextLine();
        if (taskTitle == "") {
            taskTitle = "Untitled Task?";
        }
        System.out.println("Enter a task description(optional):");
        String taskDescription = scanner.nextLine();
        if (taskDescription == ""){
            taskDescription = "Empty";
        }
        taskManager.addTask(taskTitle,taskDescription);
        System.out.println("Task added.");
    }

    public static void removeTask(Scanner scanner, TaskManager taskManager){
        scanner.nextLine();
        System.out.println("Select the task you want to delete:");
        taskManager.listTasks();
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid order value");
        }
        else {
            Integer order = scanner.nextInt();
            if (taskManager.removeTask(order.toString())) {
                System.out.println("Task removed.");
            }
            else {
                System.out.println("Invalid order value");
            }
        }
    }

    public static void toggleTaskStatus(Scanner scanner, TaskManager taskManager){
        scanner.nextLine();
        System.out.println("Select the task whose status you want to toggle:");
        taskManager.listTasks();
        if (!scanner.hasNextInt()) {
            System.out.println("Incorrect order value.");
        }
        else {
            Integer order = scanner.nextInt();
            String newStatus = "Error?";
            if (taskManager.toggleTaskStatus(order.toString())) {
                if (taskManager.getTasks().get(order-1).isStatus()){
                    newStatus = "Completed";
                }
                else {
                    newStatus = "Not Completed";
                }
                System.out.println("Task status toggled. New status:"+newStatus);
            }
            else {
                System.out.println("Invalid order value.");
            }
        }
    }
}
