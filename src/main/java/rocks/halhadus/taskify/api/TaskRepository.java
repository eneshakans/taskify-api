package rocks.halhadus.taskify.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepository {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TaskRepository() {

    }

    @PostConstruct
    public void initialize() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS tasks (" + "id TEXT PRIMARY KEY," + "title TEXT NOT NULL," + "description TEXT," + "creationDate REAL," + "status BOOLEAN," + "userId TEXT NOT NULL)");
    }

    public void addTask(Task task, String userId) {
        String cmd = "INSERT INTO tasks(id, title, description, creationDate, status, userId) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(cmd, task.getId(), task.getTitle(), task.getDescription(), task.getCreationDate(), task.isStatus(), userId);
    }

    public void removeTask(String id, String userId) {
        String cmd = "DELETE FROM tasks WHERE id = ? AND userId = ?";
        jdbcTemplate.update(cmd, id, userId);
    }

    public void updateTask(Task updatedTask, String userId) {
        String cmd = "UPDATE tasks SET status = ?, title = ?, description = ? " + "WHERE id = ? AND userId = ?";
        jdbcTemplate.update(cmd, updatedTask.isStatus(), updatedTask.getTitle(), updatedTask.getDescription(), updatedTask.getId(), userId);
    }

    public void clearAllTasks(String userId) {
        jdbcTemplate.update("DELETE FROM tasks WHERE userId = ?", userId);
    }

    public List<Task> loadTasks(String userId) {
        return jdbcTemplate.query("SELECT id, title, description, creationDate, status FROM tasks WHERE userId = ?", new Object[]{userId}, new TaskRowMapper());
    }

    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(rs.getString("id"), rs.getString("title"), rs.getString("description"), rs.getDouble("creationDate"), rs.getBoolean("status")
            );
        }
    }
}