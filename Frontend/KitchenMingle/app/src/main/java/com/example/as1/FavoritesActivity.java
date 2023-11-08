



package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.as1.api.RecipeApi;
import com.example.as1.model.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {

    private LinearLayout recipeButtonContainer;
    private List<Recipe> favorites; // Use the correct data type for favorite recipes
    private RecipeApi recipeApi; // Initialize your Retrofit API service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
        recipeApi = GetRecipeAPI(); // Initialize your Retrofit API service

        // Fetch and display the favorite recipes
//        fetchFavoriteRecipes();
    }

//    void fetchFavoriteRecipes() {
//        Call<List<Recipe>> call = recipeApi.GetAllRecipes(); // Use the appropriate API call to retrieve favorite recipes
//
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                if (response.isSuccessful()) {
//                    favorites = response.body();
//                    displayRecipeButtons();
//                } else {
//                    // Handle the error case
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                // Handle the network request failure
//            }
//        });
//    }

    void displayRecipeButtons() {
        for (Recipe recipe : favorites) {
            // Create a new button for each recipe
            Button recipeButton = new Button(this);
            recipeButton.setText(recipe.getRecipeName());

            // Set an ID for the button (you can use the recipe's ID here)
            recipeButton.setId(recipe.getRecipeId());

            // Set an onClickListener to handle button click events
            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
    }

    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("recipe_id", recipeId);
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("directions", directions);
        startActivity(intent);
    }
}



//        import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.AdapterView;
//        import android.widget.ArrayAdapter;
//        import android.widget.Button;
//        import android.widget.GridView;
//        import android.widget.LinearLayout;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import com.example.as1.api.ApiClientFactory;
//        import com.example.as1.api.UsersApi;
//        import com.example.as1.model.Recipe;
//        import com.example.as1.model.SlimCallback;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import retrofit2.Call;
//
//public class FavoritesActivity extends AppCompatActivity {
//
//    private LinearLayout recipeButtonContainer;
//    private List<Recipe> favorites; // Use the correct data type for favorite recipes
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
//
//        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
//
//        // Retrieve the list of favorite items from the database or wherever you stored them.
//        List<String> favorites = getFavoriteItems();
//
//        for (String favorite : favorites) {
//            Button button = new Button(this);
//            button.setText(favorite);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle button click for this favorite item.
//                }
//            });
//            recipeButtonContainer.addView(button);
//        }
//        displayRecipeButtons();
//
//    }
//
//    // Retrieve favorite items from the database or wherever you stored them.
//    private List<Recipe> getFavoriteItems() {
//        // Replace with your logic to fetch favorites.
//        return new ArrayList<>();
//    }
//
//    void displayRecipeButtons() {
//
//
//            for (int i = favorites.size() - 1; i >= 0; i--) {
//                Recipe recipe = favorites.get(i);
//
//
//                // Create a new button for each recipe
//                Button recipeButton = new Button(this);
//                recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name
//
//
//                // Set an ID for the button (you can use the recipe's ID here)
//                recipeButton.setRecipeId(recipe.getRecipeId());
//
//
//                // Set an onClickListener to handle button click events
//                recipeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Handle the button click, e.g., navigate to the recipe's details
//                        int recipeId = view.getRecipeId();
//                        String recipeName = recipe.getRecipeName();
//                        String ingredients = recipe.getRecipeInstructions();
//                        String directions = recipe.getRecipeInstructions();
//                        navigateToRecipeDetails(recipeId, recipeName, ingredients, directions);
//                    }
//                });
//                // Add the button to the layout
//                recipeButtonContainer.addView(recipeButton);
//            }
//    }
//    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
//        // Create an Intent to start the RecipeDetailsActivity
//        Intent intent = new Intent(this, DetailsActivity.class);
//        intent.putExtra("recipe_id", recipeId); // Pass the selected recipe ID
//        intent.putExtra("recipe_name", recipeName);
//        intent.putExtra("ingredients", ingredients);
//        intent.putExtra("directions", directions);
//
//
//        // Start the DetailsActivity
//        startActivity(intent);
//
//
//    }
//
//
////
////    private GridView gridViewRecipes;
////    // TODO: Pull specific user's recipes
////    private String[] recipes = {"Recipe1", "Recipe2", "Recipe3", "Recipe4", "Recipe5", "Recipe6"};
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_favorites);
////        UsersApi usersApi = ApiClientFactory.GetUsersApi(); // initializing retrofit service
////  //      Call<String> call = usersApi.getFavoriteRecipes(getUser());
////
////        gridViewRecipes = findViewById(R.id.gridViewRecipes);
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipes);
////        gridViewRecipes.setAdapter(adapter);
////
/////*        gridViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Intent intent = new Intent(FavoritesActivity.this, DetailsActivity.class);
////                intent.putExtra("recipe_name", recipes[position]);
////                startActivity(intent);
////            }
////        });*/
//////        getFavRecipesCall.enqueue(new Callback<List<Recipe>>() {
//////            @Override
//////            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//////                if (response.isSuccessful()) {
//////                    List<Recipe> favoriteRecipes = response.body();
//////                    favoriteRecipes.forEach(recipe -> System.out.println("Recipe: " + recipe.getName()));
//////                } else {
//////                    System.out.println("Failed to get favorite recipes: " + response.errorBody().string());
//////                }
//////            }
//////
//////            @Override
//////            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//////                t.printStackTrace();
//////            }
//////        });
////    }
//}
