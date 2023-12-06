package RoundTrip.Repository;

import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import RoundTrip.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.query.Param;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeRepositoryTest {

    @Mock
    private RecipeRepository recipeRepository;

    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Recipe recipe1;
    private Recipe recipe2;
    private Editor editor;

    @BeforeEach
    public void setUp() {
        // Create ingredients
        ingredient1 = new Ingredient("Ingredient 1");
        ingredient2 = new Ingredient("Ingredient 2");

        // Create recipes
        recipe1 = new Recipe();
        recipe2 = new Recipe();

        // Create an editor
        editor = new Editor();

        // Set up relationships
        recipe1.setEditor(editor);

        recipe2.setEditor(editor);
    }

    @Test
    public void testFindRecipesByNameStartingWith() {
        // Create a list of recipes
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock the repository method
        when(recipeRepository.findRecipesByNameStartingWith("Recipe")).thenReturn(recipes);

        // Call the method
        List<Recipe> result = recipeRepository.findRecipesByNameStartingWith("Recipe");

        // Assert that the result matches the expected list
        assertEquals(recipes, result);
    }

    @Test
    public void testFindRecipesByIngredientName() {
        // Create a list of recipes
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock the repository method
        when(recipeRepository.findRecipesByIngredientName("Ingredient 1")).thenReturn(recipes);

        // Call the method
        List<Recipe> result = recipeRepository.findRecipesByIngredientName("Ingredient 1");

        // Assert that the result matches the expected list
        assertEquals(recipes, result);
    }

    @Test
    public void testFindRecipesByIngredientSet() {
        // Create a set of ingredients
        Set<Ingredient> ingredients = new HashSet<>(Arrays.asList(ingredient1, ingredient2));

        // Create a list of recipes
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock the repository method
        when(recipeRepository.findRecipesByIngredientSet(ingredients)).thenReturn(recipes);

        // Call the method
        List<Recipe> result = recipeRepository.findRecipesByIngredientSet(ingredients);

        // Assert that the result matches the expected list
        assertEquals(recipes, result);
    }


}
