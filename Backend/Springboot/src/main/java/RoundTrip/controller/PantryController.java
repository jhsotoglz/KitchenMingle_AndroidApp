package RoundTrip.controller;

import RoundTrip.NotFoundException;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import RoundTrip.repository.PantryRepository;
import RoundTrip.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class PantryController {
    @Autowired
    PantryRepository pantryRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("pantry/{userId}")
    public Pantry getPantryForUser(@PathVariable Long userId) {
        // Retrieve pantry items for a specific user
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
            Users existingUser = user.get();
            Pantry p = pantryRepository.findByUser(existingUser);
            return p;
        }else{
            return null;
        }
    }

//    @PostMapping("pantry/add/{userId}")
//    public Pantry addItemToPantry(@PathVariable Long userId, @RequestBody Ingredient ingredient) {
        // Save a new pantry item
//        Pantry pantry = pantryRepository.findByUser(new Users(userId));
//        Set<Ingredient> pantryIngredient = pantry.getIngredients();
//        pantryIngredient.add(ingredient);
//        pantryRepository.save(pantry);
//        return pantry;

        // TODO: add ingredient not working
//        Optional<Users> user = usersRepository.findById(userId);
//        if (user.isPresent()){
//            Users existingUser = user.get();
//            Pantry p = existingUser.getPantry();
//            Set<Ingredient> ingredientSet = p.getIngredients();
//            ingredientSet.add(ingredient);
//            pantryRepository.save(p);
//            return p;
//        }else{
//            return null;
//        }
//    }

//    @DeleteMapping("pantry/delete/{pantryItemId}")
//    public void deletePantryItem(@PathVariable Long pantryItemId) {
//        // Delete a pantry item by ID
//        pantryRepository.deleteById(pantryItemId);
//    }

    // TODO: Set, increment, decrement quantity of specific ingredient
//    @PutMapping("pantry/updateQuantity/{pantryItemId}")
//    public Pantry updatePantryItemQuantity(@PathVariable Long pantryItemId, @RequestParam int newQuantity) {
//        // Find the pantry item by ID
//        Optional<Pantry> pItem = pantryRepository.findById(pantryItemId);
//        if(pItem.isPresent()){
//            // Update the quantity
//            Pantry pantryItem = pItem.get();
//            pantryItem.setQuantity(newQuantity);
//
//            // Save the updated pantry item
//            return pantryRepository.save(pItem.get());
//        }else{
//            throw new NotFoundException("Ingredient " + pantryItemId + " not found.");
//        }
//    }
//
//    // Increment the quantity of an ingredient by 1
//    @PostMapping("pantry/increment/{pantryItemId}")
//    Pantry incrementIngredientQuantity(@PathVariable Long pantryItemId) {
//        Optional<Pantry> pItem = pantryRepository.findById(pantryItemId);
//        if(pItem.isPresent()){
//            // Update the quantity
//            Pantry pantryItem = pItem.get();
//            int incr = pantryItem.getQuantity() + 1;
//            pantryItem.setQuantity(incr);
//
//            // Save the updated pantry item
//            return pantryRepository.save(pItem.get());
//        }else{
//            throw new NotFoundException("Ingredient " + pantryItemId + " not found.");
//        }
//    }
//
//    // Decrement the quantity of an ingredient by 1 and delete if quantity reaches 0
//    @PostMapping("pantry/decrement/{pantryItemId}")
//    Pantry decrementIngredientQuantity(@PathVariable Long pantryItemId) {
//        Optional<Pantry> pItem = pantryRepository.findById(pantryItemId);
//        if(pItem.isPresent()){
//            Pantry pantryItem = pItem.get();
//            int quantity = pantryItem.getQuantity();
//            if (quantity <= 1){
//                pantryRepository.delete(pantryItem);
//                return null;
//            }else{
//                int decr = quantity - 1;
//                pantryItem.setQuantity(decr);
//                return pantryRepository.save(pItem.get());
//            }
//        }else{
//            throw new NotFoundException("Ingredient " + pantryItemId + " not found.");
//        }
//    }

    // TODO: Search recipes based on ingredients in pantry
}
