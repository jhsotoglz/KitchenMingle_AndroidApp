package com.example.as1.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comments")
//@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();

    @Column
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


    public Comment() {};

    public Comment(String content) {
        this.content = content;
    }

    public Comment(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public Comment(String userName, String content, Integer rating) {
        this.userName = userName;
        this.content = content;
        this.rating = rating;
    }

    public Comment(String username, String content, Integer rating, Recipe recipe) {
        this.userName = username;
        this.content = content;
        this.rating = rating;
        this.recipe = recipe;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
