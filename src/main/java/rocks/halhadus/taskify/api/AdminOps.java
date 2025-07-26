package rocks.halhadus.taskify.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOps {
    private final TokenRevocationService tokenRevocationService;
    private final AdminService adminService;

    public AdminOps(TokenRevocationService tokenRevocationService, AdminService adminService) {
        this.tokenRevocationService = tokenRevocationService;
        this.adminService = adminService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAdmin() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (adminService.isAdmin(userId)) {
            return ResponseEntity.ok("User is admin");
        } else {
            return ResponseEntity.status(403).body("User is not admin");
        }
    }

    @PostMapping("/revoke-user-tokens")
    public ResponseEntity<?> revokeUserTokens(@RequestBody UserRevokeRequest request) {
        String adminUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!adminService.isAdmin(adminUserId)) {
            return ResponseEntity.status(403).body("Only admin users can revoke tokens");
        }
        if (adminService.isAdmin(request.getUserId())) {
            return ResponseEntity.status(403).body("You are not able to revoke tokens of an Admin user.");
        }
        tokenRevocationService.revokeUserTokens(request.getUserId());
        return ResponseEntity.ok("All tokens for user revoked successfully");
    }

    static class UserRevokeRequest {
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}

@Service
class AdminService {
    private final List<String> adminUserIds;

    public AdminService(@Value("${app.admin-ids:}") String adminIds) {
        if (adminIds == null || adminIds.trim().isEmpty()) {
            this.adminUserIds = Collections.emptyList();
        } else {
            this.adminUserIds = Arrays.asList(adminIds.split(","));
        }
    }

    public boolean isAdmin(String userId) {
        return adminUserIds.contains(userId);
    }
}