package com.example.as1;


import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;


import java.util.List;
import java.util.Set;

import android.view.View;
import android.widget.Button;

import retrofit2.Call;


/**
 * This class represents the activity for picking a recipe from the available recipes.
 * It displays a list of recipe buttons and allows the user to select a recipe.
 */
public class PickRecipeActivity extends AppCompatActivity {
    private LinearLayout recipeButtonContainer;
    private Button goToFavBtn;
    private long userIdLong;


    /**
     * Initializes the activity and sets up the layout.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickrecipe);

        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
        userIdLong = getIntent().getLongExtra("user_id", 0);
//        Button addToFavBtn = findViewById(R.id.goToFavBtn);

//        goToFavBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(PickRecipeActivity.this, FavoritesActivity.class);
//                startActivity(intent);
//            }
//        });

        // Call the displayRecipeButtons method to fetch and display recipes
        displayRecipeButtons();
    }



    /**
     * Fetches and displays recipe buttons dynamically.
     */
    void displayRecipeButtons() {
        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {


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
                        //String ingredients = recipe.getRecipeIngredients();
                        String directions = recipe.getRecipeInstructions();
                        getIngredientsForRecipe(recipeId, recipeName, directions);


                        //navigateToRecipeDetails(recipeId, recipeName, ingredients, directions);
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
        intent.putExtra("user_id", userIdLong);

        // Start the DetailsActivity
        startActivity(intent);


    }

    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);

        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
            // Handle the successful response, e.g., update UI or store the ingredients

            // Now you can use the 'ingredients' list as needed
            String ingredientsString = getIngredientsAsString(ingredients);

            // Navigate to recipe details or update UI
            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
        }, "GetIngredientsForRecipe"));
    }

    private String getIngredientsAsString(List<Ingredient> ingredients) {
        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            ingredientsStringBuilder.append(ingredient.getIngredientName()).append("\n");
        }
        return ingredientsStringBuilder.toString();
    }




}


