package RoundTrip.Model;

import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.PantryIngredient;
import RoundTrip.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    private Ingredient ingredient;
    private Recipe recipe;
    private Editor editor; // Define the Editor object

    @BeforeEach
    public void setUp() {
        ingredient = new Ingredient("Test Ingredient");
        recipe = new Recipe();
        editor = new Editor(); // Initialize the Editor object
    }

    @Test
    public void testIdGetterAndSetter() {
        ingredient.setId(1L);
        assertEquals(1L, ingredient.getId());
    }

    @Test
    public void testIngredientNameGetterAndSetter() {
        ingredient.setIngredientName("New Ingredient");
        assertEquals("New Ingredient", ingredient.getIngredientName());
    }

    @Test
    public void testToString() {
        assertEquals("Ingredient{id=null, ingredientName='Test Ingredient'}", ingredient.toString());
    }

    @Test
    public void testRecipesGetterAndSetter() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        ingredient.setRecipes(recipes);
        assertEquals(recipes, ingredient.getRecipes());
    }

    @Test
    public void testPantryIngredientGetterAndSetter() {
        Set<PantryIngredient> pantryIngredients = new HashSet<>();
        PantryIngredient pantryIngredient = new PantryIngredient();
        pantryIngredients.add(pantryIngredient);
        ingredient.setPantryIngredient(pantryIngredients);
        assertEquals(pantryIngredients, ingredient.getPantryIngredient());
    }
}
