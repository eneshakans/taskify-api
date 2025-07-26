package rocks.halhadus.taskify.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthOps {
    private final UserService userService;
    private final JwtOps jwtOps;

    public AuthOps(UserService userService, JwtOps jwtOps) {
        this.userService = userService;
        this.jwtOps = jwtOps;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            if (request.getUsername().length() < 3) {
                return ResponseEntity.badRequest().body("Username must be at least 3 characters");
            }
            if (request.getPassword().length() < 12) {
                return ResponseEntity.badRequest().body("Password must be at least 12 characters");
            }
            for (char c : request.getUsername().toCharArray()) {
                if (!Character.isLetterOrDigit(c)) {
                    return ResponseEntity.badRequest().body("Username can only contain letters and numbers");
                }
            }
            for (char c : request.getPassword().toCharArray()) {
                if (c > 127) {
                    return ResponseEntity.badRequest().body("Password can only contain ASCII characters");
                }
            }
            User user = new User(request.getUsername(), request.getPassword());
            String userId = userService.registerUser(user);
            String token = jwtOps.generateToken(userId);
            return ResponseEntity.ok(new AuthResponse(token, jwtOps.getExpirationTime() / 1000, userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Optional<User> user = userService.authenticate(request.getUsername(), request.getPassword());
            if (user.isPresent()) {
                String token = jwtOps.generateToken(user.get().getId());
                return ResponseEntity.ok(new AuthResponse(token, jwtOps.getExpirationTime() / 1000, user.get().getId()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private long expiresIn;
    private String userId;

    public AuthResponse(String token, long expiresIn, String userId) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.userId = userId;
    }

    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }
    public long getExpiresIn() { return expiresIn; }
    public String getUserId() { return userId; }
}

@Service
class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        boolean matches = false;
        matches = passwordEncoder.matches(password, user.get().getPassword());
        return matches ? user : Optional.empty();
    }
}