package RoundTrip.controller;

import RoundTrip.repository.PantryIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PantryIngredientController {
    @Autowired
    PantryIngredientRepository pantryIngredientRepository;


}
