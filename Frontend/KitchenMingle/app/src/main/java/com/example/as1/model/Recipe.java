package com.example.as1.model;

import java.util.Set;

public class Recipe {

    private int id;
    private String recipeName;
    private String recipeInstructions;
    private String recipeIngredients;
    private Set<Ingredient> ingredients;


    public Recipe() {
    }

    public int getRecipeId() {
        return id;
    }

    public void setRecipeId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public String printable(){
        return "\nRecipe Name:  " + getRecipeName()
                + "\nRecipe Instruction:  " + getRecipeInstructions() + "\n";
    }

}

