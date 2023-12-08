package com.example.as1;


import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.view.View;
import android.widget.Button;

import retrofit2.Call;

public class PickRecipeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private long userIdLong;
    private List<Recipe> allRecipes;

    private Long userId; // stores user ID from login


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
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_discover);
        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 is default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    return true;
                case R.id.nav_favorites:
                    Intent discoverIntent = new Intent(PickRecipeActivity.this, FavoritesActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_pantry:
                    Intent favoritesIntent = new Intent(PickRecipeActivity.this, MyPantryActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
            }
            return false;
        });

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

    void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);

        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
            String ingredientsString = getIngredientsAsString(ingredients);
            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
        }, "GetIngredientsForRecipe"));
    }


//    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
//        Call<List<Ingredient>> call = GetRecipeAPI().getIngredientsForRecipe((long) recipeId);
//
//        call.enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            String ingredientsString = getIngredientsAsString(ingredients);
//            navigateToRecipeDetails(recipeId, recipeName, ingredientsString, directions);
//        }, "GetIngredientsForRecipe"));
//    }

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


