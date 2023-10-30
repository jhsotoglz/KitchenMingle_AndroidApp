package RoundTrip.controller;

import RoundTrip.NotFoundException;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.PantryIngredient;
import RoundTrip.model.Users;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.PantryIngredientRepository;
import RoundTrip.repository.PantryRepository;
import RoundTrip.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class PantryIngredientController {
    @Autowired
    PantryIngredientRepository pantryIngredientRepository;

    @Autowired
    PantryRepository pantryRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    UsersRepository usersRepository;

    // Add ingredient to user pantry
    @PostMapping("pantryIng/add/{userId}/{ingredientId}")
    public Set<PantryIngredient> addPantryIngredient(@PathVariable Long userId, @PathVariable Long ingredientId){
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
            Users existingUser = user.get();
            Pantry pantry = existingUser.getPantry();
            Optional<Ingredient> ingr = ingredientRepository.findById(ingredientId);
            if(ingr.isPresent()){
                Ingredient ingredient = ingr.get();
                PantryIngredient pantryIngredient = new PantryIngredient(ingredient);
                pantryIngredient.setPantry(pantry);
                pantry.setPantryIngredient(pantryIngredient);
                pantryIngredientRepository.save(pantryIngredient);
                pantryRepository.save(pantry);
                return pantry.getPantryIngredient();
            }else{
                throw new NotFoundException("Ingredient "+ ingredientId + " not found");
            }
        }else{
            throw new NotFoundException("User "+ userId + " not found");
        }
    }

    // Manually set quantity for an ingredient stored in Pantry
    @PutMapping("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    public void setQuantity(@PathVariable Long userId, @PathVariable Long pantryIngId,
                            @PathVariable int quantity){
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
            Users existingUser = user.get();
            Pantry pantry = existingUser.getPantry();
            Optional<PantryIngredient> pantryIngr = pantryIngredientRepository.findById(pantryIngId);
            if (pantryIngr.isPresent()){
                PantryIngredient pantryIngredient = pantryIngr.get();
                pantryIngredient.setQuantity(quantity);
                pantryIngredientRepository.save(pantryIngredient);
            }else{
                throw new NotFoundException("User "+ pantryIngId + " not found");
            }
        }else{
            throw new NotFoundException("User "+ userId + " not found");
        }
    }

    // TODO: increment quantity by 1

    // TODO: decrement quantity by 1

    // Delete ingredient from pantry
    @DeleteMapping("pantryIng/delete/{userId}/{pantryIngId}")
    public void deleteIngredient(@PathVariable Long userId, @PathVariable Long pantryIngId){
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
            Optional<PantryIngredient> pantryIngr = pantryIngredientRepository.findById(pantryIngId);
            if (pantryIngr.isPresent()){
                pantryIngredientRepository.deleteById(pantryIngId);
            }else{
                throw new NotFoundException("User "+ pantryIngId + " not found");
            }
        }else{
            throw new NotFoundException("User "+ userId + " not found");
        }
    }
}
