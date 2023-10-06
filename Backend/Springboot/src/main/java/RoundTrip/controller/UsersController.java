package RoundTrip.controller;


import RoundTrip.NotFoundException;
import RoundTrip.model.LoginRequest;
import RoundTrip.model.Users;
import RoundTrip.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    UsersRepository usersRepository;

    // Show all users
    @GetMapping("users")
    List<Users> GetAllUsers(){
        return usersRepository.findAll();
    }

    // Show user based on id
    @GetMapping("users/{id}")
    Users GetUsers(@PathVariable Long id){
        Optional<Users> existingUser = usersRepository.findById(id);
        // Check if id exists
        if(existingUser.isPresent()){
            return existingUser.get();
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

    @GetMapping("users/getEmail/{email}")
    Users GetUsersByEmail(@PathVariable String email){
        Users existingUser = usersRepository.findByEmail(email);
        if(existingUser != null){
            return existingUser;
        }else{
            throw new NotFoundException("User with email "+ email + " not found");
        }
    }


    // Register new user. Check if email exists
    @PostMapping("users/register")
    ResponseEntity<String> RegisterUsers(@RequestBody Users newUser) {
        // Check if the user with the provided email already exists
        if (usersRepository.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        // Check if the username is already taken by other user
        if (usersRepository.existsByUsername(newUser.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        // Add the user to the database
        usersRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully: " + newUser.toString());
    }

    // Change user info based on id
    @PutMapping("users/put/{id}")
    Users ChangeUsers(@PathVariable Long id, @RequestBody Users newInfo){
        Optional<Users> existingUser = usersRepository.findById(id);
        // Check if id exists
        if(existingUser.isPresent()) {
            Users updateUser = existingUser.get();
            updateUser.setEmail(newInfo.getEmail());
            updateUser.setPassword(newInfo.getPassword());
            updateUser.setUsername(newInfo.getUsername());
            usersRepository.save(updateUser);
            return updateUser;
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

    // Delete user based on id
    @DeleteMapping("users/delete/{id}")
    void DeleteUsers(@PathVariable Long id){
        Optional<Users> existingUser = usersRepository.findById(id);
        // Check if id exists
        if(existingUser.isPresent()){
            usersRepository.delete(existingUser.get());
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

//    @GetMapping("users/login")
//    String login(@RequestBody Users loginUser){
//        Users dbUser = usersRepository.findByEmail(loginUser.getEmail());
//        if(dbUser != null){
//            if(dbUser.getPassword().equals(loginUser.getPassword())){
//                return "Login successful. Welcome!";
//            }
//        }
//        return "Login failed. Check your email and password.";
//    }

//    @GetMapping("users/login")
//    ResponseEntity<String> login(@RequestBody Users loginUser){
//        logger.info("recieved login request for email: " + loginUser.getEmail());
//        Users dbUser = usersRepository.findByEmail(loginUser.getEmail());
//        if(dbUser != null){
//            if(dbUser.getPassword().equals(loginUser.getPassword())){
//                logger.info("Login successful for email: "+ loginUser.getEmail());
//                return ResponseEntity.ok("Login successful. Welcome!");
//            }
//        }
//        logger.info("Login fail for email: "+ loginUser.getEmail());
//        return ResponseEntity.badRequest().body("Login failed. Check your email and password.");
//    }

    @PostMapping("users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Users dbUser = usersRepository.findByEmail(email);

        if (dbUser != null) {
            if (dbUser.getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful. Welcome!");
            }
        }

        return ResponseEntity.badRequest().body("Login failed. Check your email and password.");
    }
}