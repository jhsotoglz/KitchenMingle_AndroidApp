package RoundTrip.Controller;

import RoundTrip.controller.EditorController;
import RoundTrip.model.Editor;
import RoundTrip.model.Recipe;
import RoundTrip.repository.EditorRepository;
import RoundTrip.repository.IngredientRepository;
import RoundTrip.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
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

    // Test creating a recipe via path variables
    @Test
    public void testPostRecipeByPath() throws Exception {
        // Mock the editor returned from the repository
        Editor editor = new Editor();
        editor.setId(1L);
        when(editorRepository.findById(anyLong())).thenReturn(Optional.of(editor));

        // Mock the save method to return a recipe
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Test Recipe");
        recipe.setRecipeInstructions("Test Instructions");
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Perform the post request to create a new recipe
        mockMvc.perform(post("/recipe/post/{editorId}/{name}/{instructions}", 1L, "Test Recipe", "Test Instructions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe"))
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions"))
                .andDo(print());  // Temporarily print the response for debugging
    }

    @Test
    public void testPostRecipeByBody() throws Exception {
        // Mock the editor returned from the repository
        Editor editor = new Editor();
        editor.setId(1L);
        when(editorRepository.findById(anyLong())).thenReturn(Optional.of(editor));

        // Create a Recipe object
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Test Recipe");
        recipe.setRecipeInstructions("Test Instructions");

        // Perform the POST request to create a new recipe
        mockMvc.perform(post("/recipe/post/{editorId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Test Recipe")) // Verify recipeName in the response
                .andExpect(jsonPath("$.recipeInstructions").value("Test Instructions")) // Verify recipeInstructions in the response
                .andExpect(jsonPath("$.editor.id").value(1)); // Verify editor.id in the response
    }

    // Test deleting a recipe by ID
    @Test
    public void testDeleteRecipeById() throws Exception {
        // Mock the editor returned from the repository
        Editor editor = new Editor();
        editor.setId(1L);
        when(editorRepository.findById(anyLong())).thenReturn(Optional.of(editor));

        doNothing().when(recipeRepository).deleteById(anyLong());

        // Perform the delete request to remove a recipe
        mockMvc.perform(delete("/recipe/delete/{editorId}/{id}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
                .andExpect(jsonPath("$.editor.email", is("updated@example.com")))
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
