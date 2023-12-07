package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> allRecipes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recyclerView = findViewById(R.id.searchRecyclerView);
        recipeAdapter = new RecipeAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void displayRecipeButtons() {

         GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
             allRecipes = recipes;
             recipeAdapter.updateData(recipes);
         }, "GetAllRecipe"));
    }
}
