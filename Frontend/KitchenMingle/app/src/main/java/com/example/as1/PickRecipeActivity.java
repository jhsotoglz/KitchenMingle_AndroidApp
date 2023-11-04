package com.example.as1;


import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.as1.api.RecipeApi;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;


import java.util.ArrayList;
import java.util.List;


public class PickRecipeActivity extends AppCompatActivity {
    private LinearLayout recipeButtonContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickrecipe);


        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);


        // Call the displayRecipeButtons method to fetch and display recipes
        displayRecipeButtons();
    }
    void displayRecipeButtons() {
        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {


            for (int i = recipes.size() - 1; i >= 0; i--) {
                Recipe recipe = recipes.get(i);


                // Create a new button for each recipe
                Button recipeButton = new Button(this);
                recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name


                // Set an ID for the button (you can use the recipe's ID here)
                recipeButton.setId(recipe.getId());


                // Set an onClickListener to handle button click events
                recipeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle the button click, e.g., navigate to the recipe's details
                        int recipeId = view.getId();
                        navigateToRecipeDetails(recipeId);
                    }
                });
                // Add the button to the layout
                recipeButtonContainer.addView(recipeButton);
            }
        }, "GetAllRecipe"));
    }
    private void navigateToRecipeDetails(int recipeId) {
        // Create an Intent to start the RecipeDetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("recipe_id", recipeId); // Pass the selected recipe ID


        // Start the DetailsActivity
        startActivity(intent);


    }


}








//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pickrecipe);
//
//        // Retrieve the list of recipes (you need to replace this with your data retrieval logic)
//        List<Recipe> recipeList = getRecipeList();
//
//        // Get the layout where you want to add the recipe buttons
//        LinearLayout recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
//
//        // Dynamically create buttons for each recipe
//        for (Recipe recipe : recipeList) {
//            Button recipeButton = new Button(this);
//            recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name
//            recipeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle the button click for this recipe
//                    int recipeId = recipe.getId(); // Replace with your logic to get the recipe ID
//                    String recipeName = recipe.getRecipeName();
//
//                    // Add the recipe information to an Intent
//                    Intent intent = new Intent(PickRecipeActivity.this, DetailsActivity.class);
//                    intent.putExtra("recipe_id", recipeId);
//                    intent.putExtra("recipe_name", recipeName);
//
//                    // Start the Recipe Details Activity
//                    startActivity(intent);
//                }
//            });
//
//            // Add the button to the layout
//            recipeButtonContainer.addView(recipeButton);
//        }
//    }
//
//    // Replace this with your logic to retrieve the list of recipes
//    private List<Recipe> getRecipeList() {
//        List<Recipe> recipes = new ArrayList<>();
//
//        // Add your logic to fetch recipes from your data source (e.g., database or API)
//        // recipes.add(new Recipe(1, "Recipe 1"));
//        // recipes.add(new Recipe(2, "Recipe 2"));
//        // ...
//
//        return recipes;
//    }





