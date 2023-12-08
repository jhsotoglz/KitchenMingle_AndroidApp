package RoundTrip.Model;

import RoundTrip.model.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginRequest = new LoginRequest();
    }

    @Test
    public void testGetAndSetEmail() {
        loginRequest.setEmail("user@example.com");
        assertEquals("user@example.com", loginRequest.getEmail());
    }

    @Test
    public void testGetAndSetPassword() {
        loginRequest.setPassword("user1234");
        assertEquals("user1234", loginRequest.getPassword());
    }
}
