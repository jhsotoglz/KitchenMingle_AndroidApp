



package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
import static com.example.as1.api.ApiClientFactory.GetUsersApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.api.RecipeApi;
import com.example.as1.api.UsersApi;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.as1.model.Users;


public class FavoritesActivity extends AppCompatActivity {
    private LinearLayout recipeButtonContainer;
    private Button allRecipes;


    /**
     * Initializes the activity and sets up the layout.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Retrieve the user ID from the intent
        userIdLong = getIntent().getLongExtra("user_id", 0);

        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);

        // Call the displayRecipeButtons method to fetch and display recipes
        displayRecipeButtons();
    }

    Users user = new Users();
    int userId = user.getUserId();
    long userIdLong = (long) userId;



    /**
     * Fetches and displays recipe buttons dynamically.
     */
    void displayRecipeButtons() {
        GetUsersApi().getFavoriteRecipes(userIdLong).enqueue(new SlimCallback<List<Recipe>>(recipes -> {


            for (int i = recipes.size() - 1; i >= 0; i--) {
                Recipe recipe = recipes.get(i);


                // Create a new button for each recipe
                Button recipeButton = new Button(this);
                recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name


                // Set an ID for the button (you can use the recipe's ID here)
                recipeButton.setId(recipe.getRecipeId());


                // Set an onClickListener to handle button click events
                recipeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle the button click, e.g., navigate to the recipe's details
                        int recipeId = view.getId();
                        String recipeName = recipe.getRecipeName();
                        String ingredients = recipe.getRecipeInstructions();
                        String directions = recipe.getRecipeInstructions();
                        navigateToRecipeDetails(recipeId, recipeName, ingredients, directions);
                    }
                });
                // Add the button to the layout
                recipeButtonContainer.addView(recipeButton);
            }
        }, "GetAllRecipe"));
    }
    /**
     * Navigates to the details activity for the selected recipe.
     *
     * @param recipeId     The ID of the selected recipe.
     * @param recipeName   The name of the selected recipe.
     * @param ingredients  The ingredients of the selected recipe.
     * @param directions   The cooking directions of the selected recipe.
     */
    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
        // Create an Intent to start the RecipeDetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("recipe_id", recipeId); // Pass the selected recipe ID
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("directions", directions);


        // Start the DetailsActivity
        startActivity(intent);


    }


}

