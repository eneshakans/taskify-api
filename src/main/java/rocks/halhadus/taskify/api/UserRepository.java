package rocks.halhadus.taskify.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserRepository() {

    }

    @PostConstruct
    public void initialize() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" + "id TEXT PRIMARY KEY," + "username TEXT NOT NULL UNIQUE," + "password TEXT NOT NULL)");
    }

    public void save(User user) {
        String cmd = "INSERT INTO users(id, username, password) VALUES(?,?,?)";
        jdbcTemplate.update(cmd, user.getId(), user.getUsername(), user.getPassword());
    }

    public Optional<User> findByUsername(String username) {
        String cmd = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(cmd, new Object[]{username}, new UserRowMapper()).stream().findFirst();
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}