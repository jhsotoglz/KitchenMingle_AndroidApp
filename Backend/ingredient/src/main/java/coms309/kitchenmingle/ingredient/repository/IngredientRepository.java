package coms309.kitchenmingle.ingredient.repository;

import coms309.kitchenmingle.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
