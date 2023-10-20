package RoundTrip.controller;

import RoundTrip.NotFoundException;
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

    @GetMapping("/user/{userId}")
    public List<Pantry> getPantryForUser(@PathVariable Long userId) {
        // Retrieve pantry items for a specific user
        return pantryRepository.findByUser(new Users(userId));
    }

    @PostMapping("/add")
    public Pantry addItemToPantry(@RequestBody Pantry pantryItem) {
        // Save a new pantry item
        return pantryRepository.save(pantryItem);
    }

    @DeleteMapping("/delete/{pantryItemId}")
    public void deletePantryItem(@PathVariable Long pantryItemId) {
        // Delete a pantry item by ID
        pantryRepository.deleteById(pantryItemId);
    }

    @PutMapping("/updateQuantity/{pantryItemId}")
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
}
