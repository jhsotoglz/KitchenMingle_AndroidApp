package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PickRecipeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private long userIdLong;
    private List<Recipe> allRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickrecipe);

        recyclerView = findViewById(R.id.recyclerView);
        userIdLong = getIntent().getLongExtra("user_id", 0);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(new ArrayList<>());
        recyclerView.setAdapter(recipeAdapter);

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the submission of the search query if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle the change in the search query
                filterRecipes(newText);
                return true;
            }
        });

        displayRecipeButtons();  // Initial display without filtering
    }

    private void filterRecipes(String query) {
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            if (recipe.getRecipeName().toLowerCase().contains(query.toLowerCase())) {
                filteredRecipes.add(recipe);
            }
        }

        // Update the RecyclerView with filtered recipes
        recipeAdapter.updateData(filteredRecipes);
    }

    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("recipe_id", recipeId);
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("directions", directions);
        intent.putExtra("user_id", userIdLong);
        startActivity(intent);
    }

    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);

        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
            String ingredientsString = getIngredientsAsString(ingredients);
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

    private void displayRecipeButtons() {
        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
            allRecipes = recipes;
            recipeAdapter.updateData(recipes);
        }, "GetAllRecipe"));
    }

    private static class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
        private List<Recipe> data;

        RecipeAdapter(List<Recipe> data) {
            this.data = data;
        }

        void updateData(List<Recipe> newData) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recipe_button, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            Recipe recipe = data.get(position);
            holder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        static class RecipeViewHolder extends RecyclerView.ViewHolder {
            private final Button recipeButton;

            RecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                recipeButton = itemView.findViewById(R.id.recipeButton);
            }

            void bind(Recipe recipe) {
                recipeButton.setText(recipe.getRecipeName());
                recipeButton.setOnClickListener(view -> {
                    int recipeId = recipe.getRecipeId();
                    String recipeName = recipe.getRecipeName();
                    String directions = recipe.getRecipeInstructions();
                    // Get the activity from the context
                    PickRecipeActivity pickRecipeActivity = (PickRecipeActivity) itemView.getContext();
                    pickRecipeActivity.getIngredientsForRecipe(recipeId, recipeName, directions);
                });
            }
        }
    }
}



//
//
//package com.example.as1;
//
//
//import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.SearchView;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//
//import com.example.as1.model.Ingredient;
//import com.example.as1.model.Recipe;
//import com.example.as1.model.SlimCallback;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import android.view.View;
//import android.widget.Button;
//
//import retrofit2.Call;
//
//public class PickRecipeActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private RecipeAdapter recipeAdapter;
//    private long userIdLong;
//    private List<Recipe> allRecipes;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pickrecipe);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        userIdLong = getIntent().getLongExtra("user_id", 0);
//
//        // Set up the RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recipeAdapter = new RecipeAdapter(new ArrayList<>());
//        recyclerView.setAdapter(recipeAdapter);
//
//        SearchView searchView = findViewById(R.id.searchView);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Handle the submission of the search query if needed
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Handle the change in the search query
//                filterRecipes(newText);
//                return true;
//            }
//        });
//
//        displayRecipeButtons();  // Initial display without filtering
//    }
//
//    private void filterRecipes(String query) {
//        List<Recipe> filteredRecipes = new ArrayList<>();
//
//        for (Recipe recipe : allRecipes) {
//            if (recipe.getRecipeName().toLowerCase().contains(query.toLowerCase())) {
//                filteredRecipes.add(recipe);
//            }
//        }
//
//        // Update the RecyclerView with filtered recipes
//        recipeAdapter.updateData(filteredRecipes);
//    }
//
//    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
//        Intent intent = new Intent(this, DetailsActivity.class);
//        intent.putExtra("recipe_id", recipeId);
//        intent.putExtra("recipe_name", recipeName);
//        intent.putExtra("ingredients", ingredients);
//        intent.putExtra("directions", directions);
//        intent.putExtra("user_id", userIdLong);
//        startActivity(intent);
//    }
//
//    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
//        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);
//
//        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            String ingredientsString = getIngredientsAsString(ingredients);
//            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
//        }, "GetIngredientsForRecipe"));
//    }
//
//    private String getIngredientsAsString(List<Ingredient> ingredients) {
//        StringBuilder ingredientsStringBuilder = new StringBuilder();
//        for (Ingredient ingredient : ingredients) {
//            ingredientsStringBuilder.append(ingredient.getIngredientName()).append("\n");
//        }
//        return ingredientsStringBuilder.toString();
//    }
//
//    private void displayRecipeButtons() {
//        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
//            allRecipes = recipes;
//            recipeAdapter.updateData(recipes);
//        }, "GetAllRecipe"));
//    }
//
//    private static class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
//        private List<Recipe> data;
//
//        RecipeAdapter(List<Recipe> data) {
//            this.data = data;
//        }
//
//        void updateData(List<Recipe> newData) {
//            data.clear();
//            data.addAll(newData);
//            notifyDataSetChanged();
//        }
//
//        @NonNull
//        @Override
//        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_recipe_button, parent, false);
//            return new RecipeViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
//            Recipe recipe = data.get(position);
//            holder.bind(recipe);
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        static class RecipeViewHolder extends RecyclerView.ViewHolder {
//            private final Button recipeButton;
//
//            RecipeViewHolder(@NonNull View itemView) {
//                super(itemView);
//                recipeButton = itemView.findViewById(R.id.recipeButton);
//            }
//
//            void bind(Recipe recipe) {
//                recipeButton.setText(recipe.getRecipeName());
//                recipeButton.setOnClickListener(view -> {
//                    int recipeId = recipe.getRecipeId();
//                    String recipeName = recipe.getRecipeName();
//                    String directions = recipe.getRecipeInstructions();
//                    // Get the activity from the context
//                    PickRecipeActivity pickRecipeActivity = (PickRecipeActivity) itemView.getContext();
//                    pickRecipeActivity.getIngredientsForRecipe(recipeId, recipeName, directions);
//                });
//            }
//        }
//    }
//}
//
/////**
//// * This class represents the activity for picking a recipe from the available recipes.
//// * It displays a list of recipe buttons and allows the user to select a recipe.
//// */
////public class PickRecipeActivity extends AppCompatActivity {
////    private LinearLayout recipeButtonContainer;
////    private long userIdLong;
////    private List<Recipe> allRecipes;  // Add this list to store all recipes
////
////
////
////    /**
////     * Initializes the activity and sets up the layout.
////     *
////     * @param savedInstanceState A Bundle containing the saved state of the activity.
////     */
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_pickrecipe);
////
////        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
////        userIdLong = getIntent().getLongExtra("user_id", 0);
////
////        // Get the SearchView from the layout
////        SearchView searchView = findViewById(R.id.searchView);
////
////        // Set up a listener for the search view
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                // Handle the submission of the search query if needed
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String newText) {
////                // Handle the change in the search query
////                filterRecipes(newText);
////                return true;
////            }
////        });
////
////        // Call the displayRecipeButtons method to fetch and display recipes
////        displayRecipeButtons();
////    }
////
////
////    /**
////     * Filters recipes based on the search query and updates the displayed buttons.
////     *
////     * @param query The search query entered by the user.
////     */
////    private void filterRecipes(String query) {
////        List<Recipe> filteredRecipes = new ArrayList<>();
////
////        for (Recipe recipe : allRecipes) {
////            // You can customize the filtering logic here, for example, by checking recipe names
////            if (recipe.getRecipeName().toLowerCase().contains(query.toLowerCase())) {
////                filteredRecipes.add(recipe);
////            }
////        }
////
////        // Clear the existing buttons
////        recipeButtonContainer.removeAllViews();
////
////        // Display the filtered recipe buttons
////        displayRecipeButtons(filteredRecipes);
////    }
////
////
////    /**
////     * Fetches and displays recipe buttons dynamically.
////     */
//////    void displayRecipeButtons() {
//////        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
//////
//////
//////            for (int i = recipes.size() - 1; i >= 0; i--) {
//////                Recipe recipe = recipes.get(i);
//////
//////
//////                // Create a new button for each recipe
//////                Button recipeButton = new Button(this);
//////                recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name
//////
//////
//////                // Set an ID for the button (you can use the recipe's ID here)
//////                recipeButton.setId(recipe.getRecipeId());
//////
//////
//////                // Set an onClickListener to handle button click events
//////                recipeButton.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View view) {
//////                        // Handle the button click, e.g., navigate to the recipe's details
//////                        int recipeId = view.getId();
//////                        String recipeName = recipe.getRecipeName();
//////                        //String ingredients = recipe.getRecipeIngredients();
//////                        String directions = recipe.getRecipeInstructions();
//////                        getIngredientsForRecipe(recipeId, recipeName, directions);
//////
//////
//////                        //navigateToRecipeDetails(recipeId, recipeName, ingredients, directions);
//////                    }
//////                });
//////                // Add the button to the layout
//////                recipeButtonContainer.addView(recipeButton);
//////            }
//////        }, "GetAllRecipe"));
//////    }
////
////    private void displayRecipeButtons() {
////        // Fetch and display all recipes
////        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
////            // Call the updated displayRecipeButtons method to create buttons
////            displayRecipeButtons(recipes);
////        }, "GetAllRecipe"));
////    }
////
////    // Update the displayRecipeButtons method to accept a list of recipes
////    void displayRecipeButtons(List<Recipe> recipes) {
////        for (Recipe recipe : recipes) {
////            // Create a new button for each recipe (similar to your existing code)
////            Button recipeButton = new Button(this);
////            recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name
////
////            // Set an ID for the button (you can use the recipe's ID here)
////            recipeButton.setId(recipe.getRecipeId());
////
////            // Set an onClickListener to handle button click events
////            recipeButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    // Handle the button click, e.g., navigate to the recipe's details
////                    int recipeId = view.getId();
////                    String recipeName = recipe.getRecipeName();
////                    String directions = recipe.getRecipeInstructions();
////                    getIngredientsForRecipe(recipeId, recipeName, directions);
////                }
////            });
////
////            // Add the button to the layout
////            recipeButtonContainer.addView(recipeButton);
////        }
////    }
////
////
////
////    /**
////     * Navigates to the details activity for the selected recipe.
////     *
////     * @param recipeId     The ID of the selected recipe.
////     * @param recipeName   The name of the selected recipe.
////     * @param ingredients  The ingredients of the selected recipe.
////     * @param directions   The cooking directions of the selected recipe.
////     */
////    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
////        // Create an Intent to start the RecipeDetailsActivity
////        Intent intent = new Intent(this, DetailsActivity.class);
////        intent.putExtra("recipe_id", recipeId); // Pass the selected recipe ID
////        intent.putExtra("recipe_name", recipeName);
////        intent.putExtra("ingredients", ingredients);
////        intent.putExtra("directions", directions);
////        intent.putExtra("user_id", userIdLong);
////
////        // Start the DetailsActivity
////        startActivity(intent);
////
////
////    }
////
////    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
////        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);
////
////        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
////            // Handle the successful response, e.g., update UI or store the ingredients
////
////            // Now you can use the 'ingredients' list as needed
////            String ingredientsString = getIngredientsAsString(ingredients);
////
////            // Navigate to recipe details or update UI
////            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
////        }, "GetIngredientsForRecipe"));
////    }
////
////    private String getIngredientsAsString(List<Ingredient> ingredients) {
////        StringBuilder ingredientsStringBuilder = new StringBuilder();
////        for (Ingredient ingredient : ingredients) {
////            ingredientsStringBuilder.append(ingredient.getIngredientName()).append("\n");
////        }
////        return ingredientsStringBuilder.toString();
////    }
////
////
////
////
////}
////
//
//
//
//
//
////package com.example.as1;
////
////
////import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
////
////
////import android.content.Intent;
////import android.os.Bundle;
////import androidx.appcompat.app.AppCompatActivity;
////
////
////import android.view.View;
////import android.widget.Button;
////import android.widget.LinearLayout;
////
////
////import com.example.as1.model.Ingredient;
////import com.example.as1.model.Recipe;
////import com.example.as1.model.SlimCallback;
////
////
////import java.util.List;
////import java.util.Set;
////
////import android.view.View;
////import android.widget.Button;
////
////import retrofit2.Call;
////
////
/////**
//// * This class represents the activity for picking a recipe from the available recipes.
//// * It displays a list of recipe buttons and allows the user to select a recipe.
//// */
////public class PickRecipeActivity extends AppCompatActivity {
////    private LinearLayout recipeButtonContainer;
////    private Button goToFavBtn;
////    private long userIdLong;
////
////
////    /**
////     * Initializes the activity and sets up the layout.
////     *
////     * @param savedInstanceState A Bundle containing the saved state of the activity.
////     */
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_pickrecipe);
////
////        recipeButtonContainer = findViewById(R.id.recipeButtonContainer);
////        userIdLong = getIntent().getLongExtra("user_id", 0);
//////        Button addToFavBtn = findViewById(R.id.goToFavBtn);
////
//////        goToFavBtn.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v)
//////            {
//////                Intent intent = new Intent(PickRecipeActivity.this, FavoritesActivity.class);
//////                startActivity(intent);
//////            }
//////        });
////
////        // Call the displayRecipeButtons method to fetch and display recipes
////        displayRecipeButtons();
////    }
////
////
////
////    /**
////     * Fetches and displays recipe buttons dynamically.
////     */
////    void displayRecipeButtons() {
////        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
////
////
////            for (int i = recipes.size() - 1; i >= 0; i--) {
////                Recipe recipe = recipes.get(i);
////
////
////                // Create a new button for each recipe
////                Button recipeButton = new Button(this);
////                recipeButton.setText(recipe.getRecipeName()); // Set the button text to the recipe name
////
////
////                // Set an ID for the button (you can use the recipe's ID here)
////                recipeButton.setId(recipe.getRecipeId());
////
////
////                // Set an onClickListener to handle button click events
////                recipeButton.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        // Handle the button click, e.g., navigate to the recipe's details
////                        int recipeId = view.getId();
////                        String recipeName = recipe.getRecipeName();
////                        //String ingredients = recipe.getRecipeIngredients();
////                        String directions = recipe.getRecipeInstructions();
////                        getIngredientsForRecipe(recipeId, recipeName, directions);
////
////
////                        //navigateToRecipeDetails(recipeId, recipeName, ingredients, directions);
////                    }
////                });
////                // Add the button to the layout
////                recipeButtonContainer.addView(recipeButton);
////            }
////        }, "GetAllRecipe"));
////    }
////    /**
////     * Navigates to the details activity for the selected recipe.
////     *
////     * @param recipeId     The ID of the selected recipe.
////     * @param recipeName   The name of the selected recipe.
////     * @param ingredients  The ingredients of the selected recipe.
////     * @param directions   The cooking directions of the selected recipe.
////     */
////    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
////        // Create an Intent to start the RecipeDetailsActivity
////        Intent intent = new Intent(this, DetailsActivity.class);
////        intent.putExtra("recipe_id", recipeId); // Pass the selected recipe ID
////        intent.putExtra("recipe_name", recipeName);
////        intent.putExtra("ingredients", ingredients);
////        intent.putExtra("directions", directions);
////        intent.putExtra("user_id", userIdLong);
////
////        // Start the DetailsActivity
////        startActivity(intent);
////
////
////    }
////
////    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
////        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);
////
////        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
////            // Handle the successful response, e.g., update UI or store the ingredients
////
////            // Now you can use the 'ingredients' list as needed
////            String ingredientsString = getIngredientsAsString(ingredients);
////
////            // Navigate to recipe details or update UI
////            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
////        }, "GetIngredientsForRecipe"));
////    }
////
////    private String getIngredientsAsString(List<Ingredient> ingredients) {
////        StringBuilder ingredientsStringBuilder = new StringBuilder();
////        for (Ingredient ingredient : ingredients) {
////            ingredientsStringBuilder.append(ingredient.getIngredientName()).append("\n");
////        }
////        return ingredientsStringBuilder.toString();
////    }
////
////
////
////
////}
////
////
