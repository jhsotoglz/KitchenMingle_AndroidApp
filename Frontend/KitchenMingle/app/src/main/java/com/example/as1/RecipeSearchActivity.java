package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;
import com.example.as1.api.ApiClientFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RecipeSearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private Long userId; // stores user ID from login
    private List<Recipe> allRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        Log.d("RecipeSearchActivity", "RecipeSearchActivity");

        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 as default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(new ArrayList<>());
        recyclerView.setAdapter(recipeAdapter);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(View.NO_ID);


        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    Intent discoverIntent = new Intent(RecipeSearchActivity.this, PickRecipeActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_favorites:
                    Intent favoritesIntent = new Intent(RecipeSearchActivity.this, FavoritesActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
                case R.id.nav_pantry:
                    Intent pantryIntent = new Intent(RecipeSearchActivity.this, MyPantryActivity.class);
                    pantryIntent.putExtra("USER_ID", userId);
                    startActivity(pantryIntent);
                    return true;
            }
            return false;
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the submission of the search query if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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

        recipeAdapter.updateData(filteredRecipes);
    }

    private void navigateToRecipeDetails(int recipeId, String recipeName, String ingredients, String directions) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("recipe_id", recipeId);
        intent.putExtra("recipe_name", recipeName);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("directions", directions);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    private void getIngredientsForRecipe(int recipeId, String recipeName, String directions) {
        Call<List<Ingredient>> call = ApiClientFactory.GetRecipeAPI().getIngredientsForRecipe((long) recipeId);

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
        ApiClientFactory.GetRecipeAPI().searchRecipesByPantry(userId).enqueue(new SlimCallback<List<Recipe>>(recipes -> {
            allRecipes = recipes;
            recipeAdapter.updateData(recipes);
        }, "SearchRecipesByPantry"));
//        long dummyUserId = 1; // Replace 1 with the actual ID of the dummy user
//        ApiClientFactory.GetRecipeAPI().searchRecipesByPantry(dummyUserId).enqueue(new SlimCallback<List<Recipe>>(recipes -> {
//            allRecipes = recipes;
//            recipeAdapter.updateData(recipes);
//        }, "SearchRecipesByPantry"));

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

                    RecipeSearchActivity recipeSearchActivity = (RecipeSearchActivity) itemView.getContext();
                    recipeSearchActivity.getIngredientsForRecipe(recipeId, recipeName, directions);
                });
            }
        }
    }
}
