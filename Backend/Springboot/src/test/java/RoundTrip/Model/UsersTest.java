package RoundTrip.Model;

import RoundTrip.model.Pantry;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    private Users users;
    private Pantry pantry;

    @BeforeEach
    public void setUp() {
        users = new Users();
        pantry = new Pantry();
    }

    @Test
    public void testIdGetterAndSetter() {
        users.setId(1L);
        assertEquals(1L, users.getId());
    }

    @Test
    public void testUsernameGetterAndSetter() {
        users.setUsername("TestUser");
        assertEquals("TestUser", users.getUsername());
    }

    @Test
    public void testEmailGetterAndSetter() {
        users.setEmail("test@example.com");
        assertEquals("test@example.com", users.getEmail());
    }

    @Test
    public void testPasswordGetterAndSetter() {
        users.setPassword("password123");
        assertEquals("password123", users.getPassword());
    }

    @Test
    public void testPantryGetterAndSetter() {
        users.setPantry(pantry);
        assertEquals(pantry, users.getPantry());
    }

    @Test
    public void testToString() {
        users.setUsername("TestUser");
        users.setEmail("test@example.com");
        users.setPassword("password123");
        String expectedToString = "TestUser | test@example.com | password123";
        assertEquals(expectedToString, users.toString());
    }

    @Test
    public void testFavoriteRecipesGetterAndSetter() {
        Set<Recipe> favoriteRecipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        favoriteRecipes.add(recipe1);
        favoriteRecipes.add(recipe2);
    }
}
