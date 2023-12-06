package RoundTrip.Model;

import RoundTrip.model.Admin;
import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    public void testAdminCreation() {
        // Create an instance of Admin
        Admin admin = new Admin();
        admin.setUsername("validUsername");
        admin.setPassword("validPassword");
    }

    @Test
    public void testInvalidUsernameAndPassword() {
        // Test invalid username and password
        Admin admin = new Admin();
        admin.setUsername("");
        admin.setPassword("");
    }

    @Test
    public void testInvalidEmail() {
        // Test invalid email
        Admin admin = new Admin();
        admin.setEmail("invalidEmail");
    }

    @Test
    public void testUniqueEmailConstraint() {
        // Create two Admin instances with the same email
        Admin admin1 = new Admin();
        admin1.setEmail("test@example.com");

        Admin admin2 = new Admin();
        admin2.setEmail("test@example.com");

    }
}
