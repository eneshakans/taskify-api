package rocks.halhadus.taskify;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;

public class FileOps {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/data.json";

    public static void loadJSON(TaskManager taskManager) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            List<Task> taskList = gson.fromJson(reader, new TypeToken<List<Task>>(){}.getType());

            if (taskList != null) {
                for (Task task : taskList) {
                    taskManager.addTask(task.getTitle(), task.getDescription(), task.getCreationDate(), task.isStatus());
                }
            }
        } catch (IOException e) {
            System.err.println("JSON loading error: " + e.getMessage());
        }
    }

    public static void writeJSON(TaskManager taskManager) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(taskManager.getTasks(), writer);
        } catch (IOException e) {
            System.err.println("JSON write error: " + e.getMessage());
        }
    }
}
