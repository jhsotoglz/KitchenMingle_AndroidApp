package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeName;
    private String recipeInstructions;

    @ManyToMany
    @JoinTable(name = "recipe_ingredients", // Specify the join table name
            joinColumns = @JoinColumn(name = "recipe_id"), // Specify the column name in the join table for the Recipe
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Specify the column name in the join table for the Ingredient
    )
    @JsonIgnore
    private Set<Ingredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
