package RoundTrip.Controller;

import RoundTrip.controller.UsersController;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import RoundTrip.repository.RecipeRepository;
import RoundTrip.repository.UsersRepository;
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

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RecipeRepository recipeRepository;

    private Users user1;

    @BeforeEach
    public void setUp() {
        user1 = new Users();
        user1.setId(1L);
        user1.setEmail("user1@example.com");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(usersRepository.findByEmail("user1@example.com")).thenReturn(user1);
    }

    @Test
    public void testGetUsersByEmail() throws Exception {
        setUp();
        mockMvc.perform(get("/users/getEmail/{email}", "user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("user1@example.com")));
    }

    @Test
    public void testChangeUsers() throws Exception {
        // Arrange
        Users existingUser = new Users();
        existingUser.setId(1L);
        existingUser.setEmail("existing@example.com");

        Users updatedUserInfo = new Users();
        updatedUserInfo.setEmail("updated@example.com");
        updatedUserInfo.setPassword("newPassword");
        updatedUserInfo.setUsername("newUsername");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Act and Assert
        mockMvc.perform(put("/users/put/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUserInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("updated@example.com")))
                .andExpect(jsonPath("$.password", is("newPassword")))
                .andExpect(jsonPath("$.username", is("newUsername")));
    }

    @Test
    public void testAddFavoriteRecipe() throws Exception {
        setUp();
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/addFavRecipe/{recipeId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe added to favorites."));
    }

    @Test
    public void testAddFavoriteRecipeRecipeAlreadyInFavorites() throws Exception {
        setUp();
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        user1.getFavoriteRecipes().add(recipe);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/addFavRecipe/{recipeId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe already in Favorite"));
    }

    @Test
    public void testDeleteFavoriteRecipe() throws Exception {
        setUp();
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        user1.getFavoriteRecipes().add(recipe);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));

        // Act and Assert
        mockMvc.perform(delete("/users/{userId}/removeFavRecipe/{recipeId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe removed from favorites."));
    }

    @Test
    public void testDeleteFavoriteRecipeNotFoundInFavorites() throws Exception {
        setUp();
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));

        // Act and Assert
        mockMvc.perform(delete("/users/{userId}/removeFavRecipe/{recipeId}", 1L, 1L))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Recipe not found in user's favorites."));
    }

    @Test
    public void testGetFavoriteRecipes() throws Exception {
        setUp();
        // Arrange
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        user1.getFavoriteRecipes().addAll(Arrays.asList(recipe1, recipe2));

        // Act and Assert
        mockMvc.perform(get("/users/{userId}/favRecipe", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].id").exists());
    }
}
