package rocks.halhadus.taskify;

import java.sql.*;
import java.util.LinkedList;

public class DBOps {
    private static final String DB_PATH = System.getProperty("user.dir") + "/data.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
            createTableIfNotExists(conn);
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    public static void addTask(Task task) {
        String cmd = "INSERT INTO tasks(title, description, creationDate, status) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); PreparedStatement ps = conn.prepareStatement(cmd)) {
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setDouble(3, task.getCreationDate());
            ps.setBoolean(4, task.isStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database add task error: " + e.getMessage());
        }
    }

    public static void removeTask(Task task) {
        String cmd = "DELETE FROM tasks WHERE creationDate = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); PreparedStatement ps = conn.prepareStatement(cmd)) {
            ps.setDouble(1, task.getCreationDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database remove task error: " + e.getMessage());
        }
    }

    public static void updateTask(Task task) {
        String cmd = "UPDATE tasks SET status = ? WHERE creationDate = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); PreparedStatement ps = conn.prepareStatement(cmd)) {
            ps.setBoolean(1, task.isStatus());
            ps.setDouble(2, task.getCreationDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database update task error: " + e.getMessage());
        }
    }

    public static void clearAllTasks() {
        String cmd = "DELETE FROM tasks";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); Statement st = conn.createStatement()) {
            st.execute(cmd);
        } catch (SQLException e) {
            System.err.println("Database clear all tasks error: " + e.getMessage());
        }
    }

    public static LinkedList<Task> loadTasks() {
        LinkedList<Task> tasks = new LinkedList<>();
        String cmd = "SELECT title, description, creationDate, status FROM tasks";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(cmd)) {
            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                double creationDate = rs.getDouble("creationDate");
                boolean status = rs.getBoolean("status");
                tasks.add(new Task(title, description, creationDate, status));
            }
        } catch (SQLException e) {
            System.err.println("Database loading error: " + e.getMessage());
        }
        return tasks;
    }

    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" + "title TEXT NOT NULL," + "description TEXT," + "creationDate REAL PRIMARY KEY," + "status BOOLEAN)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}