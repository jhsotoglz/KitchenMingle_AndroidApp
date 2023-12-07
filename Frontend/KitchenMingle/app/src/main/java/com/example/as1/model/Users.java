package com.example.as1.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private int id; Changed this to Long
    private Long id;
    private String username;
    @Column(unique = true)

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> favoriteRecipes;
    public Users() {
    }

    public Long getUserId() {
        return id;
    }   // Changed to Long

    public void setUserId(Long id) {
        this.id = id;
    } // Changed to long

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

    public String toString(){
        return getUsername() +" | "+ getEmail() + " | "+ getPassword();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }


}
