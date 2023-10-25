package com.model;

public class Recipe {

    private int id;
    private String recipeName;
    private String recipeInstructions;

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String printable(){
        return "\nRecipe Name:  " + getRecipeName()
                + "\nRecipe Instruction:  " + getRecipeInstructions() + "\n";
    }

}
