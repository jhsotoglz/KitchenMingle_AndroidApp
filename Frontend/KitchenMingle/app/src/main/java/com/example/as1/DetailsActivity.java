package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DetailsActivity extends AppCompatActivity {
    private LinearLayout ingredientListLayout;
    private LinearLayout directionsListLayout;
    private TextView recipeNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipe_id", -1);
        String recipeName = intent.getStringExtra("recipe_name");


        recipeNameTextView = findViewById(R.id.recipeName);
        ingredientListLayout = findViewById(R.id.ingredientListLayout);
        directionsListLayout = findViewById(R.id.directionsListLayout);

        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);

        // Retrofit code to fetch ingredients from the backend
        Call<List<Ingredient>> call = GetIngredientAPI().getIngredientsForRecipe();

        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (response.isSuccessful()) {
                    List<Ingredient> ingredients = response.body();
                    displayIngredients(ingredients);
                } else {
                    // Handle API error
                }
            }
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                // Handle network or other errors
            }
        });


        Call<Recipe> call1 = GetRecipeAPI().getRecipeByName("yourRecipeName"); // Replace "yourRecipeName" with the actual recipe name you want to retrieve.

        call1.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call1, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    // Check if the Recipe class has a getRecipeInstructions() method that returns a single String
                    if (recipe != null) {
                        String directions = recipe.getRecipeInstructions();
                        List<String> directionsList = Collections.singletonList(directions);
                        displayDirections(directionsList);
                    }
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // Handle network or other errors
            }
        });
    }
    private void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            // Create a TextView for each ingredient and add it to the layout
            TextView textView = new TextView(this);
            textView.setText(ingredient.getIngredientName());
            ingredientListLayout.addView(textView);
        }
    }

    private void displayDirections(List<String> directions) {
        for (String direction : directions) {
            // Create a TextView for each direction and add it to the layout
            TextView textView = new TextView(this);
            textView.setText(direction);
            directionsListLayout.addView(textView);
        }
    }

}

