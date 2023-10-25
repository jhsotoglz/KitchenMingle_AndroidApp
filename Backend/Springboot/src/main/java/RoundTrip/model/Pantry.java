package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Pantry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Pantry has many-to-many relationship with the Ingredient
    // i.e. a pantry contains multiple ingredients and an ingredient to be used in multiple pantries
    @ManyToMany
    @JoinTable(name = "pantry_ingredients",
            joinColumns = @JoinColumn(name = "pantry_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();
    private int quantity;

    // Pantry has one-to-one relationship with the User entity
    // i.e. each pantry belongs to one user
    @OneToOne
    @JsonIgnore // to assure that there is no infinite loop while returning either user/pantry objects
    private Users user;

    public Pantry() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
