package RoundTrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pantry_id")
    @JsonIgnore
    private Pantry pantry;

    @ManyToMany
    @JoinTable(
            name = "favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @JsonIgnore
    private Set<Recipe> favoriteRecipes;

    public Users() {
        this.pantry = new Pantry();
        this.pantry.setUser(this);
        this.favoriteRecipes = new HashSet<>();
    }

    public Users(Long id) {
        this.id = id;
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

    public Pantry getPantry() {
        return pantry;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public String toString(){
        return getUsername() +" | "+ getEmail() + " | "+ getPassword();
    }

    public Set<Recipe> getFavoriteRecipes() {
        if (favoriteRecipes == null) {
            favoriteRecipes = new HashSet<>();
        }
        return favoriteRecipes;
    }
}

