package com.example.as1;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;

    public User(){

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String printable(){
        return "\n Username: " + getUsername() + "\n Email: " + getEmail() + "\n Password: " + getPassword() + "\n";
    }


}
