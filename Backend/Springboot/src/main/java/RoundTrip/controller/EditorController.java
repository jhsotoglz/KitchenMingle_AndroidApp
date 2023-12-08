package RoundTrip.controller;

import RoundTrip.NotFoundException;
import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import RoundTrip.repository.EditorRepository;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class EditorController {

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    // We can also do it by request param for editorId
    // Create recipe by path
    @PostMapping("/recipe/post/{editorId}/{name}/{instructions}")
    public Recipe postRecipeByPath(@PathVariable Long editorId,
                                   @PathVariable String name,
                                   @PathVariable String instructions) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

        // Create a new Recipe object
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName(name);
        newRecipe.setRecipeInstructions(instructions);
        newRecipe.setEditor(editor);

        // Save the new recipe to the repository
        recipeRepository.save(newRecipe);

        // Return the newly created recipe
        return newRecipe;
    }

    // We can also do it by request param for editorId
    // Creates and saves a new recipe by providing JSON data in the request body
    @PostMapping("/recipe/post/{editorId}")
    public Recipe postRecipeByBody(@PathVariable Long editorId, @RequestBody Recipe newRecipe) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));
        newRecipe.setEditor(editor);

        recipeRepository.save(newRecipe);
        return newRecipe;
    }

    // We can also do it by request param for editorId
    // Deletes a recipe using its ID
    @DeleteMapping("recipe/delete/{editorId}/{id}")
    void deleteRecipeById(@PathVariable Long id, @PathVariable Long editorId) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));
        recipeRepository.deleteById(id);
    }

    // We can also do it by request param for editorId
    // Create a new recipe and associate it with ingredients by specifying ingredient IDs
    @PostMapping("/recipe/post/with-ingredients/{editorId}")
    public Recipe createRecipeWithIngredients(@PathVariable Long editorId,
                                              @RequestBody Recipe newRecipe,
                                              @RequestParam List<Long> ingredientIds) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

        newRecipe.setEditor(editor);

        Recipe savedRecipe = recipeRepository.save(newRecipe);

        // Convert the List<Ingredient> to Set<Ingredient> and filter out null elements
        Set<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        savedRecipe.setIngredients(ingredients);
        recipeRepository.save(savedRecipe);

        return savedRecipe;
    }

    // Link existing ingredients to an existing recipe in a many-to-many relationship (By Path).
    // Method to associate ingredient by ID
    @PostMapping("/recipe/{recipeId}/associateIngredientById/{editorId}/{ingredientId}")
    public Recipe associateIngredientWithRecipeById(@PathVariable Long recipeId,
                                                    @PathVariable Long editorId,
                                                    @PathVariable Long ingredientId) {
        // Verify that there is an editor with the provided id
        Optional<Editor> editorOptional = editorRepository.findById(editorId);
        if (!editorOptional.isPresent()) {
            throw new RuntimeException("Editor not found");
        }

        // Verify that there is a recipe with the provided id
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found");
        }

        // Verify that there is an ingredient with the provided id
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("Ingredient not found");
        }

        // Get the Recipe and Ingredient from the optionals
        Recipe recipe = recipeOptional.get();
        Ingredient ingredient = ingredientOptional.get();

        // Associate the ingredient with the recipe
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe); // Save the updated recipe

        return recipe;
    }

    // Create and save a new ingredient by providing the name in the URL
    @PostMapping("/ingredient/post/{editorId}/{name}")
    public Ingredient postIngredientByPath(@PathVariable Long editorId, @PathVariable String name) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

        Ingredient newIngredient = new Ingredient(name);

        newIngredient.setEditor(editor);

        ingredientRepository.save(newIngredient);
        return newIngredient;
    }

    // Create and save a new ingredient by providing JSON data in the request body
    @PostMapping("/ingredient/post/{editorId}")
    public Ingredient postIngredientByBody(@PathVariable Long editorId, @RequestBody Ingredient newIngredient) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

        newIngredient.setEditor(editor);

        ingredientRepository.save(newIngredient);
        return newIngredient;
    }


    // Deletes an ingredient by its ID
    @DeleteMapping("ingredient/delete/{editorId}/{id}")
    void deleteIngredientById(@PathVariable Long id, @PathVariable Long editorId) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));
        ingredientRepository.deleteById(id);
    }

    @PostMapping("ingredient/post/with-recipe/{editorId}")
    public Ingredient createIngredientWithRecipes(@PathVariable Long editorId,
                                                  @RequestBody Ingredient newIngredient,
                                                  @RequestParam List<Long> recipeIds) {
        // Verify that there is an editor with the provided id
        Editor editor = editorRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found"));

        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        Set<Recipe> recipes = new HashSet<>(recipeRepository.findAllById(recipeIds));
        savedIngredient.setRecipes(recipes);
        return savedIngredient;
    }

    @GetMapping("editor/getEmail/{email}")
    Editor GetEditorByEmail(@PathVariable String email){
        Editor existingEditor = editorRepository.findByEmail(email);
        if(existingEditor != null){
            return existingEditor;
        }else{
            throw new NotFoundException("Editor with email "+ email + " not found");
        }
    }

    // This is implemented in the LoginController class.
//    // Register new Editor
//    @PostMapping("editor/register")
//    public ResponseEntity<String> registerEditor(@RequestBody Editor newEditor) {
//        if (editorRepository.existsByEmail(newEditor.getEmail())) {
//            return ResponseEntity.badRequest().body("Email already in use");
//        }
//
//        editorRepository.save(newEditor);
//        return ResponseEntity.ok("Editor registered successfully: " + newEditor.toString());
//    }

    // Update Editor information
    @PutMapping("editor/put/{id}")
    public Editor updateEditor(@PathVariable Long id, @RequestBody Editor newInfo) {
        Editor editor = editorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Editor with id " + id + " not found"));

        editor.setEmail(newInfo.getEmail());
        editor.setPassword(newInfo.getPassword());
        // ... other fields to update

        return editorRepository.save(editor);
    }

    // This is implemented in the LoginController class.
//    // Editor login
//    @PostMapping("editor/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        Editor dbEditor = editorRepository.findByEmail(email);
//
//        if (dbEditor != null) {
//            if (dbEditor.getPassword().equals(password)) {
//                return ResponseEntity.ok("Login successful. Welcome!");
//            }
//        }
//
//        return ResponseEntity.badRequest().body("Login failed. Check your email and password.");
//    }

    // Should editor be able to set favorite recipes??????

//    // Sets a favorite recipe for a specific editor
//    @PostMapping("editor/{editorId}/addFavRecipe/{recipeId}")
//    public ResponseEntity<String> addFavoriteRecipe(@PathVariable Long editorId, @PathVariable Long recipeId) {
//        Editor editor = editorRepository.findById(editorId)
//                .orElseThrow(() -> new NotFoundException("Editor not found"));
//        Recipe recipe = recipeRepository.findById(recipeId)
//                .orElseThrow(() -> new NotFoundException("Recipe not found"));
//
//        if (editor.getFavoriteRecipes().contains(recipe)) {
//            return ResponseEntity.ok("Recipe already in favorite");
//        }
//        editor.getFavoriteRecipes().add(recipe);
//        editorRepository.save(editor);
//
//        return ResponseEntity.ok("Recipe added to favorites");
//    }
//
//    // Remove a recipe saved in favorites for a specific editor
//    @DeleteMapping("editor/{editorId}/removeFavRecipe/{recipeId}")
//    public ResponseEntity<String> deleteFavoriteRecipe(@PathVariable Long editorId, @PathVariable Long recipeId) {
//        Editor editor = editorRepository.findById(editorId)
//                .orElseThrow(() -> new NotFoundException("Editor not found"));
//        Recipe recipe = recipeRepository.findById(recipeId)
//                .orElseThrow(() -> new NotFoundException("Recipe not found"));
//
//        if (editor.getFavoriteRecipes().contains(recipe)) {
//            editor.getFavoriteRecipes().remove(recipe);
//            editorRepository.save(editor);
//            return ResponseEntity.ok("Recipe removed from favorites");
//        } else {
//            return ResponseEntity.badRequest().body("Recipe not found in editor's favorites");
//        }
//    }
//
//    // Gets the favorite (saved) recipes of an editor
//    @GetMapping("editor/{editorId}/favRecipe")
//    public List<Recipe> getFavoriteRecipes(@PathVariable Long editorId) {
//        Editor editor = editorRepository.findById(editorId)
//                .orElseThrow(() -> new NotFoundException("Editor not found"));
//        return new ArrayList<>(editor.getFavoriteRecipes());
//    }

    // Method to associate ingredient by Name
    @PostMapping("/recipe/{recipeId}/associateIngredientByName/{editorId}/{ingredientName}")
    public Recipe associateIngredientWithRecipeByName(@PathVariable Long recipeId,
                                                      @PathVariable Long editorId,
                                                      @PathVariable String ingredientName) {
        // Verify that there is an editor with the provided id
        Optional<Editor> editorOptional = editorRepository.findById(editorId);
        if (!editorOptional.isPresent()) {
            throw new RuntimeException("Editor not found");
        }

        // Verify that there is a recipe with the provided id
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found");
        }

        // Find ingredient by name using findByIngName
        Optional<Ingredient> ingredientOptional = ingredientRepository.findByIngredientName(ingredientName);
        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("Ingredient with name " + ingredientName + " not found");
        }

        // Get the Recipe and Ingredient from the optionals
        Recipe recipe = recipeOptional.get();
        Ingredient ingredient = ingredientOptional.get();

        // Associate the ingredient with the recipe
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe); // Save the updated recipe

        return recipe;
    }
}