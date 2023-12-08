package RoundTrip.Model;

import RoundTrip.model.Comment;
import RoundTrip.model.Rating;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {
    private Comment comment;

    @Mock
    private Users user;

    @Mock
    private Date date;

    @Mock
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        comment = new Comment();
    }

    @Test
    public void testConstructor() {
        user.setId(1L);
        String username1 = "User 1";
        String content = "Comments 1";
        Integer rating = 3;
        Comment comment1 = new Comment(user, username1, content, rating, recipe);
        assertEquals(user, comment1.getUsers());
        assertEquals(username1, comment1.getUserName());
        assertEquals(content, comment1.getContent());
        assertEquals(rating, comment1.getRating());
        assertEquals(recipe, comment1.getRecipe());
    }

    @Test
    public void testGetAndSetId() {
        comment.setId(1L);
        assertEquals(1L, comment.getId());
    }

    @Test
    public void testGetAndSetUserName() {
        comment.setUserName(user.getUsername());
        assertEquals(user.getUsername(), comment.getUserName());
    }

    @Test
    public void testGetAndSetContent() {
        comment.setContent("Example");
        assertEquals("Example", comment.getContent());
    }

    @Test
    public void testGetAndSetSent() {
        comment.setSent(date);
        assertEquals(date, comment.getSent());
    }

    @Test
    public void testGetAndSetRating() {
        comment.setRating(5);
        assertEquals(5, comment.getRating());
    }

    @Test
    public void testGetAndSetRecipe() {
        comment.setRecipe(recipe);
        assertEquals(recipe, comment.getRecipe());
    }

    @Test
    public void testGetAndSetUsers() {
        comment.setUsers(user);
        assertEquals(user, comment.getUsers());
    }
}
