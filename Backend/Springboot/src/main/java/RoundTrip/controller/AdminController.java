package RoundTrip.controller;

import RoundTrip.NotFoundException;
import RoundTrip.model.Admin;
import RoundTrip.model.LoginRequest;
import RoundTrip.model.Users;
import RoundTrip.repository.AdminRepository;
import RoundTrip.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("admin")
    List<Admin> GetAllAdmin(){
        return adminRepository.findAll();
    }

    @GetMapping("admin/getId/{id}")
    Admin GetAdminById(@PathVariable Long id){
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if(existingAdmin.isPresent()){
            return existingAdmin.get();
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }
    @GetMapping("admin/getEmail/{email}")
    Admin GetAdminByEmail(@PathVariable String email){
        Admin existingAdmin = adminRepository.findByEmail(email);
        if(existingAdmin != null){
            return existingAdmin;
        }else{
            throw new NotFoundException("User with email "+ email + " not found");
        }
    }


    // Consider implement special format for admin email 'xxx@admin.com'
    @PostMapping("admin/register")
    ResponseEntity<String> RegisterAdmin(@RequestBody Admin newAdmin) {
        // Check if the user with the provided email already exists
        if (adminRepository.existsByEmail(newAdmin.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        // Check if the username is already taken by other user
        if (adminRepository.existsByUsername(newAdmin.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        // Add the user to the database
        adminRepository.save(newAdmin);
        return ResponseEntity.ok("User registered successfully: " + newAdmin.toString());
    }

    @PostMapping("admin/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Admin dbAdmin = adminRepository.findByEmail(email);

        if (dbAdmin != null) {
            if (dbAdmin.getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful. Welcome!");
            }
        }

        return ResponseEntity.badRequest().body("Login failed. Check your email and password.");
    }

    // Admin has the function to delete a user
    @DeleteMapping("admin/deleteUser/{id}")
    void DeleteUsers(@PathVariable Long id){
        Optional<Users> existingUser = usersRepository.findById(id);
        // Check if id exists
        if(existingUser.isPresent()){
            usersRepository.delete(existingUser.get());
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

    @GetMapping("admin/viewUsers")
    List<Users> GetAllUsers(){
        return usersRepository.findAll();
    }

    // Show user based on id
    @GetMapping("admin/viewUsers/{id}")
    Users GetUsers(@PathVariable Long id){
        Optional<Users> existingUser = usersRepository.findById(id);
        // Check if id exists
        if(existingUser.isPresent()){
            return existingUser.get();
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

}
