package rocks.halhadus.taskify;

import java.util.Objects;
import java.util.Scanner;

public class TaskifyMethods {
    public static void listTasks(TaskManager taskManager){
        if (taskManager.getTasks().isEmpty()){
            System.out.println("Task list is empty.");
        }
        for (Task task : taskManager.getTasks()){
            String status;
            if (task.isStatus()){
                status = "Completed";
            }
            else {
                status = "Not completed";
            }
            if (!Objects.equals(task.getDescription(), "Empty")) {
                System.out.println((taskManager.getTasks().indexOf(task)+1) + ". " + task.getTitle()+"\nDescrpition: "+task.getDescription()+"\nCreation Date and Time: "+DateOps.formatDate(DateOps.secToDate(task.getCreationDate()))+"\nStatus: "+status+"\n");
            }
            else {
                System.out.println((taskManager.getTasks().indexOf(task)+1) + ". " + task.getTitle()+"\nCreation Date and Time: "+DateOps.formatDate(DateOps.secToDate(task.getCreationDate()))+"\nStatus: "+status+"\n");
            }
        }
    }

    public static void addTask(Scanner scanner, TaskManager taskManager){
        scanner.nextLine();
        System.out.println("Enter a task title:");
        String taskTitle = scanner.nextLine();
        if (Objects.equals(taskTitle, "")) {
            taskTitle = "Untitled Task?";
        }
        System.out.println("Enter a task description(optional):");
        String taskDescription = scanner.nextLine();
        if (Objects.equals(taskDescription, "")){
            taskDescription = "Empty";
        }
        taskManager.addTask(taskTitle,taskDescription);
        System.out.println("Task added.");
    }

    public static void removeTask(Scanner scanner, TaskManager taskManager){
        scanner.nextLine();
        System.out.println("Select the task you want to delete:");
        listTasks(taskManager);
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
        listTasks(taskManager);
        if (!scanner.hasNextInt()) {
            System.out.println("Incorrect order value.");
        }
        else {
            Integer order = scanner.nextInt();
            String newStatus;
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
