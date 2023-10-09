package com.example.as1.model;

public class Ingredient {

    private int id;
    private String ingredientName;

    public Ingredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String printable(){
        return "\n Ingredient name: " + getIngredientName() + "\n";
    }
}
