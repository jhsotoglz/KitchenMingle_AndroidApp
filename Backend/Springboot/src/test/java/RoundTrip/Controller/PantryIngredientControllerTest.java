package RoundTrip.Controller;

import RoundTrip.controller.PantryIngredientController;
import RoundTrip.controller.UsersController;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.PantryIngredient;
import RoundTrip.model.Users;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.PantryIngredientRepository;
import RoundTrip.repository.PantryRepository;
import RoundTrip.repository.UsersRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PantryIngredientController.class)
public class PantryIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PantryIngredientRepository pantryIngredientRepository;

    @MockBean
    private PantryRepository pantryRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private UsersRepository usersRepository;

    private Users user;
    private Pantry pantry;
    private PantryIngredient pantryIngredient;

    @BeforeEach
    public void setUp() {
        user = new Users();
        user.setId(1L);
        user.setEmail("user1@example.com");

        pantry = new Pantry();
        pantry.setUser(user);

        pantryIngredient = new PantryIngredient(new Ingredient());
        pantryIngredient.setId(1L);
        pantryIngredient.setPantry(pantry);
        pantryIngredient.setQuantity(3); // Initial quantity

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void testAddPantryIngredient() throws Exception {
        setUp();
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(new Ingredient()));

        // Act and Assert
        mockMvc.perform(post("/pantryIng/add/{userId}/{ingredientId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))); // Adjust this based on your actual response

        verify(usersRepository, times(1)).findById(1L);
        verify(ingredientRepository, times(1)).findById(1L);
        verify(pantryRepository, times(1)).save(any(Pantry.class));
        verify(pantryIngredientRepository, times(1)).save(any(PantryIngredient.class));
    }

    @Test
    public void testSetQuantity() throws Exception {
        setUp();
        when(pantryIngredientRepository.findById(1L)).thenReturn(Optional.of(pantryIngredient));

        // Act and Assert
        mockMvc.perform(put("/pantryIng/quantity/{userId}/{pantryIngId}/{quantity}", 1L, 1L, 5))
                .andExpect(status().isOk());

        verify(usersRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).save(any(PantryIngredient.class));
    }

    @Test
    public void testIncrementQuantity() throws Exception {
        setUp();
        when(pantryIngredientRepository.findById(1L)).thenReturn(Optional.of(pantryIngredient));

        // Act and Assert
        mockMvc.perform(put("/pantryIng/increment/{userId}/{pantryIngId}", 1L, 1L))
                .andExpect(status().isOk());

        verify(usersRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).save(any(PantryIngredient.class));
    }

    @Test
    public void testDecrementQuantity() throws Exception {
        setUp();
        when(pantryIngredientRepository.findById(1L)).thenReturn(Optional.of(pantryIngredient));

        // Act and Assert
        mockMvc.perform(put("/pantryIng/decrement/{userId}/{pantryIngId}", 1L, 1L))
                .andExpect(status().isOk());

        verify(usersRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).save(any(PantryIngredient.class));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        setUp();
        when(pantryIngredientRepository.findById(1L)).thenReturn(Optional.of(pantryIngredient));

        // Act and Assert
        mockMvc.perform(delete("/pantryIng/delete/{userId}/{pantryIngId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // Adjust this based on your actual response

        verify(usersRepository, times(1)).findById(1L);
        verify(pantryIngredientRepository, times(1)).deleteById(1L);
    }
}