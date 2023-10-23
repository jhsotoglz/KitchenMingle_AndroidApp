package RoundTrip.controller;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    // Get the list containing all recipes/
    @GetMapping("recipe/all")
    List<Recipe> GetAllRecipes(){
        return recipeRepository.findAll();
    }

    // Creates and saves a new recipe by providing name and instructions in the URL
    @PostMapping("recipe/post/{name}/{instructions}")
    Recipe PostRecipeByPath(@PathVariable String name, @PathVariable String instructions){
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName(name);
        newRecipe.setRecipeInstructions(instructions);
        recipeRepository.save(newRecipe);
        return newRecipe;
    }

    // Creates and saves a new recipe by providing JSON data in the request body
    @PostMapping("recipe/post")
    Recipe PostRecipeByPath(@RequestBody Recipe newRecipe){
        recipeRepository.save(newRecipe);
        return newRecipe;
    }

    // Deletes a recipe using its ID
    @DeleteMapping("recipe/delete/{id}")
    void deleteRecipeById(@PathVariable Long id) {
        recipeRepository.deleteById(id);
    }

    // Create a new recipe and associate it with ingredients by specifying ingredient IDs
    @PostMapping("/recipe/create")
    Recipe createRecipeWithIngredients(@RequestBody Recipe newRecipe, @RequestParam List<Long> ingredientIds) {
        Recipe savedRecipe = recipeRepository.save(newRecipe);

        // Convert the (List<Ingredient> to Set<Ingredient) and filter out null elements
        Set<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toSet());

        savedRecipe.setIngredients(ingredients);
        return savedRecipe;
    }

    // Retrieve ingredients for a specific recipe
    @GetMapping("/recipe/{recipeId}/ingredients")
    Set<Ingredient> getIngredientsForRecipe(@PathVariable Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe != null) {
            return recipe.getIngredients();
        } else {
            // Handle the case where the recipe does not exist
            return ingredientRepository.findIngredientsNotInAnyRecipe();    // FIXME displays ingredients not in recipe for now
        }
    }
    //Link existing ingredients to an existing recipe in a many-to-many relationship
    @PostMapping("/recipe/{recipeId}/associateIngredients")
    Recipe associateIngredientsWithRecipe(@PathVariable Long recipeId, @RequestBody List<Long> ingredientIds) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe != null) {
            // Retrieve the existing ingredients by their IDs
            Set<Ingredient> ingredients = new HashSet<>(ingredientRepository.findAllById(ingredientIds));
            // Associate the ingredients with the recipe
            recipe.getIngredients().addAll(ingredients);
            recipeRepository.save(recipe); // Save the updated recipe
        }
        return recipe;
    }

    /*
    In the request body, provide a JSON array containing the IDs of the existing ingredients you want to associate with the recipe. For example:
        [1, 2, 3]
     */

}