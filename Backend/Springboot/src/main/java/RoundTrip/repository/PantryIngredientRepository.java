package RoundTrip.repository;

import RoundTrip.model.PantryIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {

}
