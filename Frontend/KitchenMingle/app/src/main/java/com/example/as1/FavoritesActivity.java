package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetUsersApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private LinearLayout recipeButtonContainer;
    private Button allRecipes;
    private Long userId; // stores user ID from login

    /**
     * Initializes the activity and sets up the layout.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 is default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }

        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_favorites);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    Intent discoverIntent = new Intent(FavoritesActivity.this, PickRecipeActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_favorites:
                    return true;
                case R.id.nav_pantry:
                    Intent favoritesIntent = new Intent(FavoritesActivity.this, MyPantryActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
            }
            return false;
        });

        // Call the displayRecipeButtons method to fetch and display recipes
        displayRecipeButtons();
    }

    /**
     * Fetches and displays recipe buttons dynamically.
     */
    void displayRecipeButtons() {
        GetUsersApi().getFavoriteRecipes(userId).enqueue(new SlimCallback<List<Recipe>>(recipes -> {


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





