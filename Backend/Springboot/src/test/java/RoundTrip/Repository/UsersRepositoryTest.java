package RoundTrip.Repository;

import RoundTrip.model.Pantry;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import RoundTrip.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersRepositoryTest {

    @Mock
    private UsersRepository usersRepository;

    private Users user1;
    private Users user2;
    private Pantry pantry;

    @BeforeEach
    public void setUp() {
        // Create Users
        user1 = new Users();
        user2 = new Users();

        // Create Pantry
        pantry = new Pantry();
        user1.setPantry(pantry);

        // Set up relationships
        Set<Recipe> favoriteRecipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        favoriteRecipes.add(recipe1);
        favoriteRecipes.add(recipe2);
        user1.setFavoriteRecipes(favoriteRecipes);
    }

    @Test
    public void testExistsByEmail() {
        // Mock the repository method
        when(usersRepository.existsByEmail("user1@example.com")).thenReturn(true);

        // Call the method
        boolean result = usersRepository.existsByEmail("user1@example.com");

        // Assert that the result is true
        assertTrue(result);
    }

    @Test
    public void testExistsByUsername() {
        // Mock the repository method
        when(usersRepository.existsByUsername("user1")).thenReturn(true);

        // Call the method
        boolean result = usersRepository.existsByUsername("user1");

        // Assert that the result is true
        assertTrue(result);
    }

    @Test
    public void testFindByEmail() {
        // Mock the repository method
        when(usersRepository.findByEmail("user1@example.com")).thenReturn(user1);

        // Call the method
        Users result = usersRepository.findByEmail("user1@example.com");

        // Assert that the result matches the expected user
        assertEquals(user1, result);
    }

    @Test
    public void testFindById() {
        // Mock the repository method
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));

        // Call the method
        Optional<Users> result = usersRepository.findById(1L);

        // Assert that the result contains user1
        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
    }

    @Test
    public void testFindById_UserNotFound() {
        // Mock the repository method
        when(usersRepository.findById(3L)).thenReturn(Optional.empty());

        // Call the method
        Optional<Users> result = usersRepository.findById(3L);

        // Assert that the result is empty
        assertFalse(result.isPresent());
    }
}

