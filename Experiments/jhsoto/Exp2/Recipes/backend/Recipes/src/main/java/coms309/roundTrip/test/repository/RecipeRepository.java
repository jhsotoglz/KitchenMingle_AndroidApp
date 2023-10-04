package coms309.roundTrip.test.repository;

import coms309.roundTrip.test.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
