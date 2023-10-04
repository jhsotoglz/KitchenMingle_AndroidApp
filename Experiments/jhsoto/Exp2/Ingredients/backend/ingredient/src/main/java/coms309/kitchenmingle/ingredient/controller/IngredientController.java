package coms309.kitchenmingle.ingredient.controller;

import coms309.kitchenmingle.ingredient.model.Ingredient;
import coms309.kitchenmingle.ingredient.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping("ingredient/all")
    List<Ingredient> GetAllIngredients() {
        return ingredientRepository.findAll();
    }

    @PostMapping("ingredient/post/{name}")
    Ingredient PostIngredientByPath(@PathVariable String name){
        Ingredient newIngredient = new Ingredient();
        newIngredient.setIngredientName(name);
        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    @PostMapping("ingredient/post")
    Ingredient PostIngredientByPath(@RequestBody Ingredient newIngredient){
        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    @DeleteMapping("ingredient/delete/{id}")
    void deleteIngredientById(@PathVariable Long id) {
        ingredientRepository.deleteById(id);
    }

}

