package RoundTrip.Model;

import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditorTest {

    @Test
    public void testInvalidUsernameAndPassword() {
        // Test invalid username and password
        Editor editor = new Editor();
        editor.setUsername("");
        editor.setPassword("");

        // Assert that the username and password are empty strings
        assertEquals("", editor.getUsername());
        assertEquals("", editor.getPassword());
    }

    @Test
    public void testInvalidEmail() {
        // Test invalid email
        Editor editor = new Editor();
        editor.setEmail("invalidEmail");

        // Assert that the email is set to "invalidEmail"
        assertEquals("invalidEmail", editor.getEmail());
    }

    @Test
    public void testEntityIdentity() {
        // Create an instance of Editor and save it
        Editor editor = new Editor();
        editor.setUsername("validUsername");
        editor.setPassword("validPassword");

        // Assert that the username and password are correctly set
        assertEquals("validUsername", editor.getUsername());
        assertEquals("validPassword", editor.getPassword());
    }
}
