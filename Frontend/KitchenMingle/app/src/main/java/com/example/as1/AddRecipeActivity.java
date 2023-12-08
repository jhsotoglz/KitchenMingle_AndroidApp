package com.example.as1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.as1.api.EditorApi;
import com.example.as1.model.Recipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText;
    private EditText recipeDirectionsEditText;
    private EditText ingredientEditText;
    private Button addIngredientButton;
    private Button addRecipeButton;

    private Recipe newRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialize UI components
        recipeNameEditText = findViewById(R.id.editTextRecipeName);
        recipeDirectionsEditText = findViewById(R.id.editTextDirections);
        ingredientEditText = findViewById(R.id.editTextIngredient);
        addIngredientButton = findViewById(R.id.buttonAddIngredient);
        addRecipeButton = findViewById(R.id.buttonAddRecipe);

        newRecipe = new Recipe();

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredient();
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecipe();
            }
        });
    }

    private void addIngredient() {
        String ingredientName = ingredientEditText.getText().toString();
        // Add the ingredient to your recipe object or UI
    }

    private void addRecipe() {
        String recipeName = recipeNameEditText.getText().toString();
        String recipeDirections = recipeDirectionsEditText.getText().toString();

        // Set the recipe details
        newRecipe.setRecipeName(recipeName);
        newRecipe.setRecipeInstructions(recipeDirections);

        // Retrofit setup (modify base URL as needed)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://coms-309-033.class.las.iastate.edu:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EditorApi editorApi = retrofit.create(EditorApi.class);

        // Make a network call to add the recipe
        Call<Recipe> call = editorApi.postRecipeByBody(1L, newRecipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    // Recipe added successfully
                    Toast.makeText(AddRecipeActivity.this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(AddRecipeActivity.this, "Failed to add recipe. Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // Handle network failure
                Toast.makeText(AddRecipeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
