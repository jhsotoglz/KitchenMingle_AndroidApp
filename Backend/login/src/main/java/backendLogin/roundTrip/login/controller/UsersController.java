package backendLogin.roundTrip.login.controller;


import backendLogin.roundTrip.login.model.Users;
import backendLogin.roundTrip.login.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("users")
    List<Users> GetAllUsers(){
        return usersRepository.findAll();
    }

    @PostMapping("users/post/{name}/{e}/{p}")
    Users PostUsers(@PathVariable String name, @PathVariable String e, String p){
        Users newUser = new Users();
        newUser.setUsername(name);
        newUser.setEmail(e);
        newUser.setPassword(p);
        usersRepository.save(newUser);
        return newUser;
    }

    @PostMapping("users/post")
    Users PostUsers(@RequestBody Users newUser){
        usersRepository.save(newUser);
        return newUser;
    }
}