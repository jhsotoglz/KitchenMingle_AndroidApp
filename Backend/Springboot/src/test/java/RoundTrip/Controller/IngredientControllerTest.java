package RoundTrip.Controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import RoundTrip.controller.IngredientController;
import RoundTrip.model.Ingredient;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private RecipeRepository recipeRepository;

    private List<Ingredient> ingredientList;

    @Before
    public void setUp() {
        Ingredient ingredient1 = new Ingredient("Salt");
        Ingredient ingredient2 = new Ingredient("Sugar");
        ingredientList = Arrays.asList(ingredient1, ingredient2);

        when(ingredientRepository.findAll()).thenReturn(ingredientList);
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        mockMvc.perform(get("/ingredient/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ingredientList.size())))
                .andExpect(jsonPath("$[0].ingredientName", is(ingredientList.get(0).getIngredientName())))
                .andExpect(jsonPath("$[1].ingredientName", is(ingredientList.get(1).getIngredientName())));
    }
}

