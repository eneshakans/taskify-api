package rocks.halhadus.taskify.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskOps {
    private final TaskService taskService;

    public TaskOps(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.getAllTasks(userId);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.createTask(request.getTitle(), request.getDescription(), userId);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable String id, @RequestBody TaskRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = new Task(id, request.getTitle(), request.getDescription(), null, request.isStatus());
        taskService.updateTask(task, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        taskService.deleteTask(id, userId);
    }

    @DeleteMapping
    public void clearAllTasks() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        taskService.clearAllTasks(userId);
    }

    static class TaskRequest {
        private String title;
        private String description;
        private boolean status;
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public boolean isStatus() { return status; }
        public void setStatus(boolean status) { this.status = status; }
    }
}

@Service
class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(String userId) {
        return taskRepository.loadTasks(userId);
    }

    public Task createTask(String title, String description, String userId) {
        Task task = new Task(title, description);
        taskRepository.addTask(task, userId);
        return task;
    }

    public void updateTask(Task updatedTask, String userId) {
        taskRepository.updateTask(updatedTask, userId);
    }

    public void deleteTask(String id, String userId) {
        taskRepository.removeTask(id, userId);
    }

    public void clearAllTasks(String userId) {
        taskRepository.clearAllTasks(userId);
    }
}