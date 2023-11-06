package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class PantryIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pantry_id")
    @JsonBackReference
    private Pantry pantry;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    @JsonBackReference
    private Ingredient ingredient;

    private int quantity;

    // Default constructor
    public PantryIngredient() {
    }

    // Constructor that sets new ingredient quantity to 1 by default
    public PantryIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
        this.quantity = 1;
    }

    // Constructor that adds ingredient and set quantity by user
    public PantryIngredient(Ingredient ingredient, int quantity){
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pantry getPantry() {
        return pantry;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
