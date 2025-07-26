package rocks.halhadus.taskify.api;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenRevocationService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TokenRevocationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS revoked_users (user_id TEXT PRIMARY KEY, revocation_time INTEGER)");
    }

    public void revokeUserTokens(String userId) {
        long currentTime = System.currentTimeMillis();
        jdbcTemplate.update("INSERT OR REPLACE INTO revoked_users(user_id, revocation_time) VALUES(?, ?)", userId, currentTime);
    }

    public boolean isTokenRevoked(String userId, long issuedAt) {
        try {
            Long revocationTime = jdbcTemplate.queryForObject("SELECT revocation_time FROM revoked_users WHERE user_id = ?", (rs, rowNum) -> rs.getLong("revocation_time"), userId);
            return revocationTime != null && issuedAt < revocationTime;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
