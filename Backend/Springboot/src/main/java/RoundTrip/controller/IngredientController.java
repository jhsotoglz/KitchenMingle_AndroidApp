package RoundTrip.controller;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // Gets the list with all the ingredients
    @GetMapping("ingredient/all")
    List<Ingredient> GetAllIngredients() {
        return ingredientRepository.findAll();
    }

    // Create and save a new ingredient by providing the name in the URL
    @PostMapping("ingredient/post/{name}")
    Ingredient PostIngredientByPath(@PathVariable String name){
        Ingredient newIngredient = new Ingredient(name, 0);
        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    // Create and save a new ingredient by providing JSON data in the request body
    @PostMapping("ingredient/post")
    Ingredient PostIngredientByPath(@RequestBody Ingredient newIngredient){
        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    // Deletes an ingredient by its ID
    @DeleteMapping("ingredient/delete/{id}")
    void deleteIngredientById(@PathVariable Long id) {
        ingredientRepository.deleteById(id);
    }

    // Increment the quantity of an ingredient by 1
    @PostMapping("ingredient/increment/{id}")
    Ingredient incrementIngredientQuantity(@PathVariable Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        if (ingredient != null) {
            ingredient.setQuantity(ingredient.getQuantity() + 1);
            ingredientRepository.save(ingredient);
        }
        return ingredient;
    }

    // Decrement the quantity of an ingredient by 1 and delete if quantity reaches 0
    @PostMapping("ingredient/decrement/{id}")
    Ingredient decrementIngredientQuantity(@PathVariable Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        if (ingredient != null && ingredient.getQuantity() > 0) {
            ingredient.setQuantity(ingredient.getQuantity() - 1);
            ingredientRepository.save(ingredient);
        } else if (ingredient != null && ingredient.getQuantity() == 1) {
            // If quantity becomes 0, delete the ingredient
            ingredientRepository.delete(ingredient);
            return null; // Return null as the ingredient no longer exists
        }
        return ingredient;
    }

    // Create a new ingredient and associate it with recipes by specifying recipe IDs
    @PostMapping("/ingredient/create")
    Ingredient createIngredientWithRecipes(@RequestBody Ingredient newIngredient, @RequestParam List<Long> recipeIds) {
        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        Set<Recipe> recipes = new HashSet<>(recipeRepository.findAllById(recipeIds));
        savedIngredient.setRecipes(recipes);
        return savedIngredient;
    }

    // Retrieve recipes that use a specific ingredient
    @GetMapping("/ingredient/{ingredientId}/recipes")
    List<Recipe> getRecipesUsingIngredient(@PathVariable Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        if (ingredient != null) {
            return new ArrayList<>(ingredient.getRecipes());
        } else {
            // Handle the case where the ingredient does not exist
            return null;
        }
    }

}

