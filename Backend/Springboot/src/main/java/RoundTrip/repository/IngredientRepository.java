package RoundTrip.repository;

import RoundTrip.model.Ingredient;
import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("SELECT i FROM Ingredient i WHERE i NOT IN (SELECT r.ingredients FROM Recipe r)")
    Set<Ingredient> findIngredientsNotInAnyRecipe();

    Ingredient findByIngredientName(Ingredient ingredient);
}

