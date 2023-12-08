package RoundTrip.Model;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.PantryIngredient;
import RoundTrip.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PantryTest {
    private Pantry pantry;

    private Ingredient ingredient1 = new Ingredient();
    private Ingredient ingredient2 = new Ingredient();
    private PantryIngredient pantryIngredient1 = new PantryIngredient(ingredient1);
    private PantryIngredient pantryIngredient2 = new PantryIngredient(ingredient2);

    private Set<PantryIngredient> pantryIngredientSet = new HashSet<>();
    private Set<Ingredient> ingredientSet = new HashSet<>();

    @Mock
    private Users user1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pantry = new Pantry();
        pantryIngredientSet.add(pantryIngredient1);
        pantryIngredientSet.add(pantryIngredient2);
        ingredientSet.add(ingredient1);
        ingredientSet.add(ingredient2);
    }

    @Test
    public void testGetAndSetId() {
        pantry.setId(1L);
        assertEquals(1L, pantry.getId());
    }

    @Test
    public void testGetAndSetUser() {
        pantry.setUser(user1);
        assertEquals(user1, pantry.getUser());
    }

    @Test
    public void testGetAndSetPantryIngredient() {
        pantry.setPantryIngredient(pantryIngredient1);
        pantry.setPantryIngredient(pantryIngredient2);
        assertEquals(pantryIngredientSet, pantry.getPantryIngredient());
    }

    @Test
    public void testFindPantryIngredient() {
        pantry.setPantryIngredient(pantryIngredient1);
        assertTrue(pantry.findPantryIngredient(ingredient1));
        assertFalse(pantry.findPantryIngredient(ingredient2));  // Ingredient 2 is not added to pantry for this test
    }

    @Test
    public void testGetPantryIngredient() {
        pantry.setPantryIngredient(pantryIngredient1);
        pantry.setPantryIngredient(pantryIngredient2);
        assertEquals(ingredientSet, pantry.getIngredientFromPantry());
    }
}
