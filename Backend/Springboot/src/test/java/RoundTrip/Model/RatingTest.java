package RoundTrip.Model;

import RoundTrip.model.Rating;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {
    private Rating rating;

    @Mock
    private Recipe recipe;

    @Mock
    private Users users;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rating = new Rating();
    }

    @Test
    public void testGetAndSetId() {
        rating.setId(1L);
        assertEquals(1L, rating.getId());
    }

    @Test
    public void testGetAndSetUserName() {
        rating.setUserName("User1");
        assertEquals("User1", rating.getUserName());
        rating.setUserName(users.getUsername());
        assertEquals(users.getUsername(), rating.getUserName());
    }

    @Test
    public void testGetAndSetRecipe() {
        rating.setRecipe(recipe);
        assertEquals(recipe, rating.getRecipe());
    }

    @Test
    public void testGetAndSetUsers() {
        rating.setUsers(users);
        assertEquals(users, rating.getUsers());
    }

    @Test
    public void testGetAndSetRating() {
        rating.setRating(5);
        assertEquals(5, rating.getRating());
    }

    @Test
    public  void testSetRatingOutOfBound() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rating.setRating(6);  // An invalid rating outside the range
        });

        assertEquals("Rating must be between 1-5", exception.getMessage());
    }
}
