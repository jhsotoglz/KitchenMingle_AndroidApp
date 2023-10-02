package coms309.roundTrip.test.controller;

import coms309.roundTrip.test.model.Recipe;
import coms309.roundTrip.test.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("recipe/all")
    List<Recipe> GetAllRecipes(){
        return recipeRepository.findAll();
    }

    @PostMapping("recipe/post/{name}/{instructions}")
    Recipe PostRecipeByPath(@PathVariable String name, @PathVariable String instructions){
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName(name);
        newRecipe.setRecipeInstructions(instructions);
        recipeRepository.save(newRecipe);
        return newRecipe;
    }

    @PostMapping("recipe/post")
    Recipe PostRecipeByPath(@RequestBody Recipe newRecipe){
        recipeRepository.save(newRecipe);
        return newRecipe;
    }
}