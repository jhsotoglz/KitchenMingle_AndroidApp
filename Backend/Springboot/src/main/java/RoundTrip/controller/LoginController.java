package RoundTrip.controller;

import RoundTrip.SupportClasses.LoginResponse;
import RoundTrip.SupportClasses.RegistrationRequest;
import RoundTrip.model.Admin;
import RoundTrip.model.Editor;
import RoundTrip.model.LoginRequest;
import RoundTrip.model.Users;
import RoundTrip.repository.AdminRepository;
import RoundTrip.repository.EditorRepository;
import RoundTrip.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    EditorRepository editorRepository;

    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity<?> unifiedRegister(@RequestBody RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();
        String password = registrationRequest.getPassword();
        String username = registrationRequest.getUsername();

        try {
            switch (registrationRequest.getUserType()) {
                case "Admin" -> saveAdmin(username, email, password);
                case "Editor" -> saveEditor(username, email, password);
                case "User" -> saveUser(username, email, password);
                default -> {
                    logger.error("Invalid user type: {}", registrationRequest.getUserType());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
                }
            }
        } catch (DataAccessException e) {
            logger.error("Database access error for user: {} - {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database access error");
        } catch (Exception e) {
            logger.error("Unexpected error for user: {} - {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }

        logger.info("User registered successfully: {}", username);
        return ResponseEntity.ok("User registered successfully");
    }

    private void saveAdmin(String username, String email, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(password);
        adminRepository.save(admin);
    }

    private void saveEditor(String username, String email, String password) {
        Editor editor = new Editor();
        editor.setUsername(username);
        editor.setEmail(email);
        editor.setPassword(password);
        editorRepository.save(editor);
    }

    private void saveUser(String username, String email, String password) {
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        usersRepository.save(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> unifiedLogin(@RequestBody LoginRequest loginRequest) {
        // Check Admin
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin != null && admin.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Admin", admin.getId()));
        }

        // Check Editor
        Editor editor = editorRepository.findByEmail(loginRequest.getEmail());
        if (editor != null && editor.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Editor", editor.getId()));
        }

        // Check User
        Users user = usersRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("User", user.getId()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}
