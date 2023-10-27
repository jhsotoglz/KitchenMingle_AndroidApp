package RoundTrip.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientName;
    private int quantity;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes; // Use Set<Recipe> instead of List<Recipe>

//    @ManyToMany(mappedBy = "ingredients")
//    private Set<Pantry> pantry;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PantryIngredient> pantryIngredient = new HashSet<>();

    // Constructors
    public Ingredient() {
        // Default constructor
    }

    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
        this.quantity = 1;  // everytime a new ingredient is added, starting quantity is 1
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }

    // Getter for the 'recipes' field
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    // Setter for the 'recipes' field
    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<PantryIngredient> getPantryIngredient() {
        return pantryIngredient;
    }

    public void setPantryIngredient(Set<PantryIngredient> pantryIngredient) {
        this.pantryIngredient = pantryIngredient;
    }
}
