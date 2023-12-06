package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Pantry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Pantry has one-to-one relationship with the User entity
    // i.e. each pantry belongs to one user
    @OneToOne(mappedBy = "pantry")
    @JsonIgnore // to assure that there is no infinite loop while returning either user/pantry objects
    private Users user;

    // Pantry has many-to-many relationship with the Ingredient
    // i.e. a pantry contains multiple ingredients and an ingredient to be used in multiple pantries
//    @ManyToMany
//    @JoinTable(name = "pantry_ingredients",
//            joinColumns = @JoinColumn(name = "pantry_id"),
//            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
//    private Set<Ingredient> ingredients = new HashSet<>();

    // One pantry can store many items
    @OneToMany(mappedBy = "pantry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PantryIngredient> pantryIngredient = new HashSet<>();


    public Pantry() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<PantryIngredient> getPantryIngredient() {
        return pantryIngredient;
    }

//    public void setPantryIngredient(Set<PantryIngredient> pantryIngredient) {
//        this.pantryIngredient = pantryIngredient;
//    }

    public void setPantryIngredient(PantryIngredient pantryIngredient) {
        this.pantryIngredient.add(pantryIngredient);
    }

    public boolean findPantryIngredient(Ingredient ingredient){
        for (PantryIngredient pantryIng : pantryIngredient) {
            Ingredient existingIngredient = pantryIng.getIngredient();
            if(existingIngredient.equals(ingredient)){
                return true;
            }
        }
        return false;
    }

    // Retrieve ingredients from pantry ingredients
    public Set<Ingredient> getPantryIngredients() {
        return pantryIngredient.stream()
                .map(PantryIngredient::getIngredient)
                .collect(Collectors.toSet());
    }
}
