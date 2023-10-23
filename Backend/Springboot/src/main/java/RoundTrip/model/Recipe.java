package RoundTrip.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String recipeName;
    private String recipeInstructions;

    @ManyToMany
    @JoinTable(name = "recipe_ingredients", // Specify the join table name
            joinColumns = @JoinColumn(name = "recipe_id"), // Specify the column name in the join table for the Recipe
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Specify the column name in the join table for the Ingredient
    )
    private Set<Ingredient> ingredients;

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

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // Getter method for ingredients
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
}
