package RoundTrip.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    // Relationship with Ingredients
    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> createdIngredients;

    // Relationship with Recipes
    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recipe> createdRecipes;

    public Editor() {
        // Default constructor
    }

    public Editor(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Ingredient> getCreatedIngredients() {
        return createdIngredients;
    }

    public void setCreatedIngredients(Set<Ingredient> createdIngredients) {
        this.createdIngredients = createdIngredients;
    }

    public Set<Recipe> getCreatedRecipes() {
        return createdRecipes;
    }

    public void setCreatedRecipes(Set<Recipe> createdRecipes) {
        this.createdRecipes = createdRecipes;
    }
}