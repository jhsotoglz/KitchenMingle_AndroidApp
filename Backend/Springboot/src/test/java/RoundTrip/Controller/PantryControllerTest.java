package RoundTrip.Controller;

import RoundTrip.controller.PantryController;
import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import RoundTrip.repository.PantryRepository;
import RoundTrip.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PantryController.class)
public class PantryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PantryRepository pantryRepository;

    @MockBean
    private UsersRepository usersRepository;


    @Test
    public void testGetPantryForUser() throws Exception {
        // Arrange
        Users user = new Users();
        user.setId(1L);
        Pantry pantry = new Pantry();
        pantry.setId(1L);
        pantry.setUser(user);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(pantryRepository.findByUser(user)).thenReturn(pantry);

        // Act and Assert
        mockMvc.perform(get("/pantry/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
