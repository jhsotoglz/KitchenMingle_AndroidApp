package backendLogin.roundTrip.login.controller;


import backendLogin.roundTrip.login.NotFoundException;
import backendLogin.roundTrip.login.model.Users;
import backendLogin.roundTrip.login.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("users")
    List<Users> GetAllUsers(){
        return usersRepository.findAll();
    }

    @PostMapping("users/register")
    Users AddUsers(@RequestBody Users newUser){
        usersRepository.save(newUser);
        return newUser;
    }

    @PutMapping("users/put/{id}")
    Users ChangeUsers(@PathVariable Long id, @RequestBody Users newInfo){
        Optional<Users> existingUser = usersRepository.findById(id);
        if(existingUser.isPresent()) {
            Users updateUser = existingUser.get();
            updateUser.setEmail(newInfo.getEmail());
            updateUser.setPassword(newInfo.getPassword());
            updateUser.setUsername(newInfo.getUsername());
            usersRepository.save(updateUser);
            return updateUser;
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }

    @DeleteMapping("users/delete/{id}")
    void DeleteUsers(@PathVariable Long id){
        Optional<Users> existingUser = usersRepository.findById(id);
        if(existingUser.isPresent()){
            usersRepository.delete(existingUser.get());
        }else{
            throw new NotFoundException("User with id "+ id + " not found");
        }
    }
}