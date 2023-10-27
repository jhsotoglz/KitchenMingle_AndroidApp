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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    // TODO: consider input ingredient name
    @PostMapping("pantryIng/add/{userId}/{ingredientId}")
    public PantryIngredient addPantryIngredient(@PathVariable Long userId,
                                                @PathVariable Long ingredientId){
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()){
            Users existingUser = user.get();
            Pantry pantry = pantryRepository.findByUser(existingUser);

            Optional<Ingredient> ingr = ingredientRepository.findById(ingredientId);
            if(ingr.isPresent()){
                Ingredient ingredient = ingr.get();
                PantryIngredient pantryIngredient = new PantryIngredient(ingredient);
                return pantryIngredientRepository.save(pantryIngredient);
            }else{
                throw new NotFoundException("Ingredient "+ ingredientId + " not found");
            }
        }else{
            throw new NotFoundException("User "+ userId + " not found");
        }

    }

}
