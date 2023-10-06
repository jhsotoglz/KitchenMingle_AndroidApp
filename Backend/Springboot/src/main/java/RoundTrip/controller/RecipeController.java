package RoundTrip.controller;

import RoundTrip.model.Recipe;
import RoundTrip.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

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
}