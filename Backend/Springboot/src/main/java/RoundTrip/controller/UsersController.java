package RoundTrip.controller;


import RoundTrip.NotFoundException;
import RoundTrip.model.Users;
import RoundTrip.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
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
}