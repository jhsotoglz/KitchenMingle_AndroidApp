package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientName;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private Set<Recipe> recipes; // Use Set<Recipe> instead of List<Recipe>

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PantryIngredient> pantryIngredient = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "editor_id")
    private Editor editor;

    // Constructors
    public Ingredient() {
        // Default constructor
    }

    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
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

    public void setEditor(Editor editor) {
        this.editor = editor; // Assigns the passed Editor object to the editor field of this Ingredient instance.
    }

    public Editor getEditor() {
        return editor;
    }
}
