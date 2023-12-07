package RoundTrip.Model;

import RoundTrip.model.Comment;
import RoundTrip.model.Rating;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        comment = new Comment();
    }

    @Test
    public  void testGetAndSetId() {
        comment.setId(1L);
        assertEquals(1L, comment.getId());
    }

    @Test
    public  void testGetAndSetUserName() {
        comment.setUserName(user.getUsername());
        assertEquals(user.getUsername(), comment.getUserName());
    }

    @Test
    public  void testGetAndSetContent() {
        comment.setContent("Example");
        assertEquals("Example", comment.getContent());
    }

    @Test
    public  void testGetAndSetSent() {
        comment.setSent(date);
        assertEquals(date, comment.getSent());
    }

    @Test
    public  void testGetAndSetRating() {
        comment.setRating(5);
        assertEquals(5, comment.getRating());
    }

    @Test
    public  void testGetAndSetUsers() {
        comment.setUsers(user);
        assertEquals(user, comment.getUsers());
    }
}
