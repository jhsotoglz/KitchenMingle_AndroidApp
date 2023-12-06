package RoundTrip.Model;

import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeTest {

    private Recipe recipe;

    @Mock
    private Editor editor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipe = new Recipe();
    }

    @Test
    public void testGetAndSetId() {
        recipe.setId(1L);
        assertEquals(1L, recipe.getId());
    }

    @Test
    public void testGetAndSetRecipeName() {
        recipe.setRecipeName("Test Recipe");
        assertEquals("Test Recipe", recipe.getRecipeName());
    }

    @Test
    public void testGetAndSetRecipeInstructions() {
        recipe.setRecipeInstructions("Instructions for the recipe.");
        assertEquals("Instructions for the recipe.", recipe.getRecipeInstructions());
    }

    @Test
    public void testGetAndSetIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient1 = new Ingredient("Ingredient 1");
        Ingredient ingredient2 = new Ingredient("Ingredient 2");
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        recipe.setIngredients(ingredients);
        assertEquals(ingredients, recipe.getIngredients());
    }
}
