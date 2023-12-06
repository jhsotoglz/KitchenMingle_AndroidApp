package RoundTrip.Repository;

import RoundTrip.model.Editor;
import RoundTrip.repository.EditorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditorRepositoryTest {

    @Mock
    private EditorRepository editorRepository;

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";
        when(editorRepository.existsByEmail(email)).thenReturn(true);
        boolean exists = editorRepository.existsByEmail(email);
        assertTrue(exists);
    }

    @Test
    public void testExistsByUsername() {
        String username = "testuser";
        when(editorRepository.existsByUsername(username)).thenReturn(true);
        boolean exists = editorRepository.existsByUsername(username);
        assertTrue(exists);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Editor editor = new Editor();
        editor.setEmail(email);
        when(editorRepository.findByEmail(email)).thenReturn(editor);

        Editor foundEditor = editorRepository.findByEmail(email);

        assertNotNull(foundEditor);
        assertEquals(email, foundEditor.getEmail());
    }

    @Test
    public void testFindByEmailNotFound() {
        String email = "nonexistent@example.com";
        when(editorRepository.findByEmail(email)).thenReturn(null);

        Editor foundEditor = editorRepository.findByEmail(email);

        assertNull(foundEditor);
    }

}

