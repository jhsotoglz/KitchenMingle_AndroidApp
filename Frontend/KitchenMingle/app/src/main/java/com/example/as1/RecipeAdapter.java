package com.example.as1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
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
