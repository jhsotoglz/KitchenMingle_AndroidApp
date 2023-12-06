package RoundTrip.Repository;

import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import RoundTrip.repository.PantryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PantryRepositoryTest {

    @Mock
    private PantryRepository pantryRepository;

    private Users user1;
    private Pantry pantry1;

    @BeforeEach
    public void setUp() {
        user1 = new Users();
        user1.setId(1L);
        pantry1 = new Pantry();
        user1.setPantry(pantry1);
    }

    @Test
    void testFindByUser() {
       when(pantryRepository.findByUser(user1)).thenReturn(pantry1);
       Pantry result = pantryRepository.findByUser(user1);
       assertEquals(pantry1,result);
    }

    @Test
    public void testFindById() {
        when(pantryRepository.findById(1L)).thenReturn(Optional.of(pantry1));
        Optional<Pantry> result = pantryRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(pantry1, result.get());
    }

    @Test
    public void testFindById_NotFound() {
        when(pantryRepository.findById(100L)).thenReturn(Optional.empty());
        Optional<Pantry> result = pantryRepository.findById(100L);
        assertFalse(result.isPresent());
    }
}