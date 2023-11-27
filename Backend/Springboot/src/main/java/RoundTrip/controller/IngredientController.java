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

//    // Create and save a new ingredient by providing the name in the URL
//    @PostMapping("ingredient/post/{name}")
//    Ingredient PostIngredientByPath(@PathVariable String name){
//        Ingredient newIngredient = new Ingredient(name);
//        ingredientRepository.save(newIngredient);
//        return newIngredient;
//    }
//
//    // Create and save a new ingredient by providing JSON data in the request body
//    @PostMapping("ingredient/post")
//    Ingredient PostIngredientByPath(@RequestBody Ingredient newIngredient){
//        ingredientRepository.save(newIngredient);
//        return newIngredient;
//    }

//    // Deletes an ingredient by its ID
//    @DeleteMapping("ingredient/delete/{id}")
//    void deleteIngredientById(@PathVariable Long id) {
//        ingredientRepository.deleteById(id);
//    }
//
//    // Create a new ingredient and associate it with recipes by specifying recipe IDs
//    @PostMapping("/ingredient/create")
//    Ingredient createIngredientWithRecipes(@RequestBody Ingredient newIngredient, @RequestParam List<Long> recipeIds) {
//        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
//        Set<Recipe> recipes = new HashSet<>(recipeRepository.findAllById(recipeIds));
//        savedIngredient.setRecipes(recipes);
//        return savedIngredient;
//    }

}

