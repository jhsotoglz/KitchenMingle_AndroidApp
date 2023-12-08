
package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetEditorAPI;
import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as1.api.EditorApi;
import com.example.as1.api.RecipeApi;
import com.example.as1.model.Ingredient;
import com.example.as1.model.SlimCallback;
import com.example.as1.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * This class represents the activity for managing recipes.
 * It allows users to view, add, and manage recipes.
 */
public class RecipeActivity extends AppCompatActivity {

    private EditorApi editorApi;
    private List<String> currentRecipeIngredients = new ArrayList<>(); // Data structure to store ingredients

    private Long userId; // stores user ID from login

    /**
     * Initializes the activity and sets up the layout.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Initialize EditorApi
        editorApi = GetEditorAPI();

        TextView apiText1 = findViewById(R.id.txtView_IngList);

        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);
        RegenerateAllRecipesOnScreen(apiText1);

        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 as default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }

        Button PostByPathBtn = findViewById(R.id.activity_main_post_by_path_button);
//        Button PostByBodyBtn = findViewById(R.id.addIngrBtn);
        EditText recipeNameIn = findViewById(R.id.activity_main_recipename_editText);
        EditText instructionIn = findViewById(R.id.activity_main_instruction_editText);

        EditText ingredientIn = findViewById(R.id.activity_main_ingredient_editText);
        Button addIngredientBtn = findViewById(R.id.addIngredientBtn);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(View.NO_ID);


        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    Intent discoverIntent = new Intent(RecipeActivity.this, PickRecipeActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_favorites:
                    Intent favoritesIntent = new Intent(RecipeActivity.this, FavoritesActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
                case R.id.nav_pantry:
                    Intent pantryIntent = new Intent(RecipeActivity.this, MyPantryActivity.class);
                    pantryIntent.putExtra("USER_ID", userId);
                    startActivity(pantryIntent);
                    return true;
            }
            return false;
        });


        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the entered ingredient name
                String ingredientName = ingredientIn.getText().toString();

                // Check if the ingredient name is not empty
                if (!TextUtils.isEmpty(ingredientName)) {
                    // Add the ingredient to the data structure
                    currentRecipeIngredients.add(ingredientName);

                    // Display a message or update UI if needed
                    Toast.makeText(RecipeActivity.this, "Ingredient added: " + ingredientName, Toast.LENGTH_SHORT).show();

                    // Clear the ingredient input field
                    ingredientIn.setText("");
                } else {
                    // Display an error or notify the user that the ingredient name is required
                    Toast.makeText(RecipeActivity.this, "Please enter an ingredient name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RegenerateAllRecipesOnScreen(apiText1);

        PostByPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editorApi.postRecipeByPath(recipeNameIn.getText().toString(), instructionIn.getText().toString())
                        .enqueue(new SlimCallback<Recipe>(recipe -> {
                            RegenerateAllRecipesOnScreen(apiText1);
                            recipeNameIn.setText("");
                            instructionIn.setText("");
                        }));
                RegenerateAllRecipesOnScreen(apiText1);

            }
        });

//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Recipe newRecipe = new Recipe();
//                newRecipe.setRecipeName(recipeNameIn.getText().toString());
//                newRecipe.setRecipeInstructions(instructionIn.getText().toString());
//                editorApi.postRecipeByBody(1L, newRecipe) // Replace 1L with the appropriate editorId
//                        .enqueue(new SlimCallback<Recipe>(recipe -> {
//                            RegenerateAllRecipesOnScreen(apiText1);
//                            recipeNameIn.setText("");
//                            instructionIn.setText("");
//                        }));
//                RegenerateAllRecipesOnScreen(apiText1);
//            }
//        });
    }

    /**
     * Refreshes the UI by fetching and displaying all recipes.
     *
     * @param apiText1 The TextView where the recipe list is displayed.
     */
//    void RegenerateAllRecipesOnScreen(TextView apiText1) {
//        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
//            apiText1.setText("");
//
//            for (int i = recipes.size() - 1; i >= 0; i--) {
//                Recipe currentRecipe = recipes.get(i);
//                apiText1.append(currentRecipe.printable());
//                apiText1.append("\nIngredients:\n");
//                getIngredientsForRecipe(currentRecipe);
//            }
//        }, "GetAllRecipe"));
//    }

    void RegenerateAllRecipesOnScreen(TextView apiText1) {
        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
            apiText1.setText("");

            for (int i = recipes.size() - 1; i >= 0; i--) {
                Recipe currentRecipe = recipes.get(i);
                apiText1.append(currentRecipe.printable());

                // Call the method to get and append ingredients for the current recipe
//                getIngredientsForRecipe(currentRecipe);
                int recipeId = currentRecipe.getRecipeId();
                String recipeName = currentRecipe.getRecipeName();
                String directions = currentRecipe.getRecipeInstructions();

                //getIngredientsForRecipe(apiText1, recipeId, recipeName, directions);
            }
        }, "GetAllRecipe"));
    }

//    void getIngredientsForRecipe(TextView apiText1, int recipeId, String recipeName, String directions) {
//        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);
//
//        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            String ingredientsString = getIngredientsAsString(ingredients);
//            apiText1.append("\nIngredients for " + recipeName + ":\n" + ingredientsString);
//
//        }, "GetIngredientsForRecipe"));
//    }



    void getIngredientsForRecipe(Recipe recipe) {
        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipe.getRecipeId());

        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
            // Format ingredients
            String ingredientsString = getIngredientsAsString(ingredients);

            // Display the formatted ingredients in the TextView
            TextView apiText1 = findViewById(R.id.txtView_IngList);
            apiText1.append(ingredientsString);
        }, "GetIngredientsForRecipe"));
    }


    String getIngredientsAsString(List<Ingredient> ingredients) {
        // Convert the list of ingredients to a formatted string
        StringBuilder stringBuilder = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            stringBuilder.append("- ")
                    .append(ingredient.getIngredientName())
                    .append("\n");
        }

        return stringBuilder.toString();
    }

}
