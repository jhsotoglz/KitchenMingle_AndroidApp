package RoundTrip.Controller;

import RoundTrip.controller.RecipeController;
import RoundTrip.model.Recipe;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import RoundTrip.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        // Mock data setup
        Recipe recipe1 = new Recipe(); // Set properties for recipe1
        Recipe recipe2 = new Recipe(); // Set properties for recipe2
        Recipe recipe3 = new Recipe(); // Set properties for recipe3
        List<Recipe> allRecipes = Arrays.asList(recipe1, recipe2, recipe3);

        when(recipeRepository.findAll()).thenReturn(allRecipes);
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        mockMvc.perform(get("/recipe/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // Assuming there are 3 recipes
    }
}
