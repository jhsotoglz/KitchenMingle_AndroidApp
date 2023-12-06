package RoundTrip.Repository;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import RoundTrip.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientRepositoryTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    public void testFindIngredientsNotInAnyRecipe() {
        // Create some ingredients
        Ingredient ingredient1 = new Ingredient("Ingredient 1");
        Ingredient ingredient2 = new Ingredient("Ingredient 2");

        // Create a set of ingredients not in any recipe
        Set<Ingredient> ingredientsNotInAnyRecipe = new HashSet<>();
        ingredientsNotInAnyRecipe.add(ingredient1);
        ingredientsNotInAnyRecipe.add(ingredient2);

        // Mock the repository method
        when(ingredientRepository.findIngredientsNotInAnyRecipe()).thenReturn(ingredientsNotInAnyRecipe);

        // Call the method
        Set<Ingredient> result = ingredientRepository.findIngredientsNotInAnyRecipe();

        // Assert that the result matches the expected set
        assertEquals(ingredientsNotInAnyRecipe, result);
    }

    @Test
    public void testFindByIngredientName() {
        String ingredientName = "Ingredient Name";
        Ingredient ingredient = new Ingredient(ingredientName);

        // Mock the repository method
        when(ingredientRepository.findByIngredientName(ingredient)).thenReturn(ingredient);

        // Call the method
        Ingredient result = ingredientRepository.findByIngredientName(ingredient);

        // Assert that the result matches the expected ingredient
        assertNotNull(result);
        assertEquals(ingredientName, result.getIngredientName());
    }

    @Test
    public void testFindByIngredientNameNotFound() {
        String ingredientName = "Nonexistent Ingredient";
        Ingredient ingredient = new Ingredient(ingredientName);

        // Mock the repository method to return null
        when(ingredientRepository.findByIngredientName(ingredient)).thenReturn(null);

        // Call the method
        Ingredient result = ingredientRepository.findByIngredientName(ingredient);

        // Assert that the result is null
        assertNull(result);
    }
}

