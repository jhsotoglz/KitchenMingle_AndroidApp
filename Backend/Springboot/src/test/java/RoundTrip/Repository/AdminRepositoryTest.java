package RoundTrip.Repository;

import RoundTrip.model.Admin;
import RoundTrip.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminRepositoryTest {

    @Mock
    private AdminRepository adminRepository;

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";
        when(adminRepository.existsByEmail(email)).thenReturn(true);
        boolean exists = adminRepository.existsByEmail(email);
        assertTrue(exists);
    }

    @Test
    public void testExistsByUsername() {
        String username = "testuser";
        when(adminRepository.existsByUsername(username)).thenReturn(true);
        boolean exists = adminRepository.existsByUsername(username);
        assertTrue(exists);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Admin admin = new Admin();
        admin.setEmail(email);
        when(adminRepository.findByEmail(email)).thenReturn(admin);

        Admin foundAdmin = adminRepository.findByEmail(email);

        assertNotNull(foundAdmin);
        assertEquals(email, foundAdmin.getEmail());
    }

    @Test
    public void testFindByEmailNotFound() {
        String email = "nonexistent@example.com";
        when(adminRepository.findByEmail(email)).thenReturn(null);

        Admin foundAdmin = adminRepository.findByEmail(email);

        assertNull(foundAdmin);
    }
}
