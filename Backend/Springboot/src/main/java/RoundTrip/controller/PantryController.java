package RoundTrip.controller;

import RoundTrip.NotFoundException;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import RoundTrip.repository.PantryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PantryController {
    @Autowired
    PantryRepository pantryRepository;

    @GetMapping("/pantry/{userId}")
    public List<Pantry> getPantryForUser(@PathVariable Long userId) {
        // Retrieve pantry items for a specific user
        return pantryRepository.findByUser(new Users(userId));
    }

    @PostMapping("/pantry/add")
    public Pantry addItemToPantry(@RequestBody Pantry pantryItem) {
        // Save a new pantry item
        return pantryRepository.save(pantryItem);
    }

    @DeleteMapping("/pantry/delete/{pantryItemId}")
    public void deletePantryItem(@PathVariable Long pantryItemId) {
        // Delete a pantry item by ID
        pantryRepository.deleteById(pantryItemId);
    }

    @PutMapping("/pantry/updateQuantity/{pantryItemId}")
    public Pantry updatePantryItemQuantity(@PathVariable Long pantryItemId, @RequestParam int newQuantity) {
        // Find the pantry item by ID
        Optional<Pantry> pItem = pantryRepository.findById(pantryItemId);
        if(pItem.isPresent()){
            // Update the quantity
            Pantry pantryItem = pItem.get();
            pantryItem.setQuantity(newQuantity);

            // Save the updated pantry item
            return pantryRepository.save(pItem.get());
        }else{
            throw new NotFoundException("Ingredient " + pantryItemId + " not found.");
        }
    }

    // Increment the quantity of an ingredient by 1
//    @PostMapping("ingredient/increment/{id}")
//    Ingredient incrementIngredientQuantity(@PathVariable Long id) {
//        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
//        if (ingredient != null) {
//            ingredient.setQuantity(ingredient.getQuantity() + 1);
//            ingredientRepository.save(ingredient);
//        }
//        return ingredient;
//    }
//
//    // Decrement the quantity of an ingredient by 1 and delete if quantity reaches 0
//    @PostMapping("ingredient/decrement/{id}")
//    Ingredient decrementIngredientQuantity(@PathVariable Long id) {
//        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
//        if (ingredient != null && ingredient.getQuantity() > 0) {
//            ingredient.setQuantity(ingredient.getQuantity() - 1);
//            ingredientRepository.save(ingredient);
//        } else if (ingredient != null && ingredient.getQuantity() == 1) {
//            // If quantity becomes 0, delete the ingredient
//            ingredientRepository.delete(ingredient);
//            return null; // Return null as the ingredient no longer exists
//        }
//        return ingredient;
//    }
}
