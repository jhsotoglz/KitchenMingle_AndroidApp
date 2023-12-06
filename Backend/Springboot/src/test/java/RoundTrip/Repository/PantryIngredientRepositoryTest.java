package RoundTrip.Repository;

import RoundTrip.model.PantryIngredient;
import RoundTrip.repository.PantryIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PantryIngredientRepositoryTest {

    @Mock
    private PantryIngredientRepository pantryIngredientRepository;

    private PantryIngredient pantryIngredient1;

    @BeforeEach
    public void setUp() {
        pantryIngredient1 = new PantryIngredient();
    }

    @Test
    public void testFindById() {
        when(pantryIngredientRepository.findById(1L)).thenReturn(Optional.of(pantryIngredient1));
        Optional<PantryIngredient> result = pantryIngredientRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(pantryIngredient1, result.get());
    }

    @Test
    public void testFindById_NotFound() {
        when(pantryIngredientRepository.findById(100L)).thenReturn(Optional.empty());
        Optional<PantryIngredient> result = pantryIngredientRepository.findById(100L);
        assertFalse(result.isPresent());
    }
}
