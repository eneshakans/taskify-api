package rocks.halhadus.taskify;

import java.util.Scanner;

public class Taskify {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Integer exit = 0;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        FileOps.loadJSON(taskManager);
        while (exit == 0){
            FileOps.writeJSON(taskManager);
            Integer val;
            System.out.println("Taskify");
            System.out.println("Choose what you want to do:\n1. Add Task\n2. Remove Task\n3. Toggle Task Status\n4. Clear Task List\n5. List Tasks\n6. Quit");
            if (scanner.hasNextInt()) {
                val = scanner.nextInt();
            }
            else {
                System.out.println("Incorrect value");
                scanner.nextLine();
                continue;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            switch (val) {
                case 1:
                    TaskifyMethods.addTask(scanner,taskManager);
                    break;
                case 2:
                    if (!taskManager.getTasks().isEmpty()){
                        TaskifyMethods.removeTask(scanner,taskManager);
                    }
                    else {
                        System.out.println("Task list is empty");
                    }
                    break;
                case 3:
                    if (!taskManager.getTasks().isEmpty()){
                        TaskifyMethods.toggleTaskStatus(scanner,taskManager);
                    }
                    else {
                        System.out.println("Task list is empty");
                    }
                    break;
                case 4:
                    taskManager.clearTaskList();
                    System.out.println("Task list cleared.");
                    break;
                case 5:
                    TaskifyMethods.listTasks(taskManager);
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    exit = 1;
                    break;
                default:
                    System.out.println("Error?");
                    exit = 1;
            }
        }
    }
}