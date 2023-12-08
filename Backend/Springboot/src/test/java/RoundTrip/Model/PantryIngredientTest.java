package RoundTrip.Model;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.PantryIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PantryIngredientTest {
    private PantryIngredient pantryIngredient;

    @Mock
    private Ingredient ingredient;

    @Mock
    private Pantry pantry;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pantryIngredient = new PantryIngredient();
    }

    @Test
    public void testConstructor1() {
        PantryIngredient pantryIngredient1 = new PantryIngredient(ingredient);
        assertEquals(ingredient, pantryIngredient1.getIngredient());
        assertEquals(1, pantryIngredient1.getQuantity());
    }

    @Test
    public void testConstructor2() {
        PantryIngredient pantryIngredient2 = new PantryIngredient(ingredient, 8);
        assertEquals(ingredient, pantryIngredient2.getIngredient());
        assertEquals(8, pantryIngredient2.getQuantity());
    }

    @Test
    public void testGetAndSetId() {
        pantryIngredient.setId(1L);
        assertEquals(1L, pantryIngredient.getId());
    }

    @Test
    public void testGetAndSetPantry() {
        pantryIngredient.setPantry(pantry);
        assertEquals(pantry, pantryIngredient.getPantry());
    }

    @Test
    public void testGetAndSetIngredient() {
        pantryIngredient.setIngredient(ingredient);
        assertEquals(ingredient, pantryIngredient.getIngredient());
    }

    @Test
    public void testGetAndSetQuantity() {
        pantryIngredient.setQuantity(3);
        assertEquals(3, pantryIngredient.getQuantity());
    }
}
