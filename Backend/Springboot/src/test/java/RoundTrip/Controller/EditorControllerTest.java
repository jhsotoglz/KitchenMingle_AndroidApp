package RoundTrip.Controller;

import RoundTrip.controller.EditorController;
import RoundTrip.model.Editor;
import RoundTrip.model.Ingredient;
import RoundTrip.model.Recipe;
import RoundTrip.repository.EditorRepository;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any; // or hamcrest?
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EditorController.class)
public class EditorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EditorRepository editorRepository;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    private Editor editor;
    @BeforeEach
    public void setUp() {
        editor = new Editor();
        editor.setId(1L);
        editor.setEmail("editor@example.com");
        when(editorRepository.findById(1L)).thenReturn(Optional.of(editor));
        when(editorRepository.findByEmail("editor@example.com")).thenReturn(editor);
    }

    // Test creating a recipe via path variables
    @Test
    public void testPostRecipeByPath() throws Exception {
        setUp();

        mockMvc.perform(post("/recipe/post/{editorId}/{name}/{instructions}", 1L, "Test Recipe", "Test Instructions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe"))
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions"));
    }


    @Test
    public void testPostRecipeByBody() throws Exception {
        setUp();
        // Create a Recipe object
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Test Recipe");
        recipe.setRecipeInstructions("Test Instructions");

        // Mock the save method to return a recipe
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Long editorId = editor.getId();
        // Perform the POST request to create a new recipe
        mockMvc.perform(post("/recipe/post/{editorId}", editorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe"))
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions"))
                .andExpect(jsonPath("$.editor.id").value(editorId));
    }

    // Test deleting a recipe by ID
    @Test
    public void testDeleteRecipeById() throws Exception {
        setUp();

        doNothing().when(recipeRepository).deleteById(anyLong());

        // Perform the delete request to remove a recipe
        mockMvc.perform(delete("/recipe/delete/{editorId}/{id}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateRecipeWithIngredients() throws Exception {
        setUp();
        // Create a list of ingredient IDs
        List<Long> ingredientIds = Arrays.asList(2L, 3L);

        // Create a new recipe with ingredients
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName("Test Recipe");
        newRecipe.setRecipeInstructions("Test Instructions");

        // Mock the save methods to return the recipe and ingredients
        when(recipeRepository.save(any(Recipe.class))).thenReturn(newRecipe);

        Ingredient ingredient1 = new Ingredient("Ingredient 1");
        Ingredient ingredient2 = new Ingredient("Ingredient 2");

        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(ingredient2));

        // Perform the POST request to create a new recipe with ingredients
        mockMvc.perform(post("/recipe/post/with-ingredients/{editorId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newRecipe))
                        .param("ingredientIds", "2", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe"))
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions"));

    }

    @Test
    public void testAssociateIngredientWithRecipe() throws Exception {
        setUp();
        // Create a list of ingredient IDs
        List<Long> ingredientIds = Arrays.asList(2L, 3L);

        // Create a new recipe with ingredients
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName("Test Recipe");
        newRecipe.setRecipeInstructions("Test Instructions");

        // Mock the save methods to return the recipe and ingredients
        when(recipeRepository.save(any(Recipe.class))).thenReturn(newRecipe);

        Ingredient ingredient1 = new Ingredient("Ingredient 1");
        Ingredient ingredient2 = new Ingredient("Ingredient 2");

        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(ingredient2));

        // Perform the POST request to create a new recipe with ingredients
        mockMvc.perform(post("/recipe/post/with-ingredients/{editorId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newRecipe))
                        .param("ingredientIds", "2", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe"))
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions"));

    }

    @Test
    public void testPostIngredientByPath() throws Exception {
        setUp();

        mockMvc.perform(post("/ingredient/post/{editorId}/{name}", 1L, "Test Ingredient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientName").value("Test Ingredient"));
    }

    @Test
    public void testPostIngredientByBody() throws Exception {
        setUp();
        // Create a Recipe object
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName("Test Ingredient");

        // Mock the save method to return a recipe
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Long editorId = editor.getId();
        // Perform the POST request to create a new recipe
        mockMvc.perform(post("/ingredient/post/{editorId}", editorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientName").value("Test Ingredient"))
                .andExpect(jsonPath("$.editor.id").value(editorId));
    }

    @Test
    public void testDeleteIngredientById() throws Exception {
        setUp();

        doNothing().when(ingredientRepository).deleteById(anyLong());

        // Perform the delete request to remove a recipe
        mockMvc.perform(delete("/ingredient/delete/{editorId}/{id}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEditorByEmail() throws Exception {
        setUp();

        mockMvc.perform(get("/editor/getEmail/{email}", "editor@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("editor@example.com")));
    }

    @Test
    public void testUpdateEditor() throws Exception {
        // Create a mock editor object
        Editor existingEditor = new Editor();
        existingEditor.setId(1L);
        existingEditor.setEmail("original@example.com");
        // Mock the findById method to return the existing editor
        when(editorRepository.findById(1L)).thenReturn(Optional.of(existingEditor));

        // Create the updated editor object with new information
        Editor updatedInfo = new Editor();
        updatedInfo.setEmail("updated@example.com");
        // Mock the save method to just return the updated editor
        when(editorRepository.save(any(Editor.class))).thenReturn(updatedInfo);

        // Perform the PUT request to update the editor
        mockMvc.perform(put("/editor/put/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("updated@example.com")))
                .andDo(print());
    }

    // Utility method to convert an object to JSON string
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
