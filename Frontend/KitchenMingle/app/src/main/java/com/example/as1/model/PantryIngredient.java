package com.example.as1.model;

public class PantryIngredient {
    private int id;
    private String ingredientName;
    private int quantity;

    // Constructor, other fields, getters, and other methods...

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PantryIngredient() {
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
        return "\n" + getIngredientName() + " " + getQuantity() + "\n";
    }

}
