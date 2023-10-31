package RoundTrip.controller;


import RoundTrip.NotFoundException;
import RoundTrip.model.LoginRequest;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import RoundTrip.repository.UsersRepository;
import RoundTrip.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RecipeRepository recipeRepository;

    // Show all users
//    @GetMapping("users")
//    List<Users> GetAllUsers(){
//        return usersRepository.findAll();
//    }
//
//    // Show user based on id
//    @GetMapping("users/{id}")
//    Users GetUsers(@PathVariable Long id){
//        Optional<Users> existingUser = usersRepository.findById(id);
//        // Check if id exists
//        if(existingUser.isPresent()){
//            return existingUser.get();
//        }else{
//            throw new NotFoundException("User with id "+ id + " not found");
//        }
//    }

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
//    @DeleteMapping("users/delete/{id}")
//    void DeleteUsers(@PathVariable Long id){
//        Optional<Users> existingUser = usersRepository.findById(id);
//        // Check if id exists
//        if(existingUser.isPresent()){
//            usersRepository.delete(existingUser.get());
//        }else{
//            throw new NotFoundException("User with id "+ id + " not found");
//        }
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

    // Sets a favorite recipe for a specific user
    @PostMapping("users/{userId}/addFavRecipe/{recipeId}")
    ResponseEntity<String> addFavoriteRecipe(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundException("Recipe not found"));

        user.getFavoriteRecipes().add(recipe);
        usersRepository.save(user);

        return ResponseEntity.ok("Recipe added to favorites.");
    }

    // Remove a recipe saved in favorites for a specific user
    @DeleteMapping("users/{userId}/removeFavRecipe/{recipeId}")
    ResponseEntity<String> deleteFavoriteRecipe(@PathVariable Long userId, @PathVariable Long recipeId){
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundException("Recipe not found"));

        if (user.getFavoriteRecipes().contains(recipe)){
            user.getFavoriteRecipes().remove(recipe);
            usersRepository.save(user);
            return ResponseEntity.ok("Recipe removed from favorites.");
        }else {
            return ResponseEntity.badRequest().body("Recipe not found in user's favorites.");
        }
    }

    // Gets the favorite (saved) recipes of a user
    @GetMapping("users/{userId}/favRecipe")
    List<Recipe> getFavoriteRecipes(@PathVariable Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new ArrayList<>(user.getFavoriteRecipes());
    }

}