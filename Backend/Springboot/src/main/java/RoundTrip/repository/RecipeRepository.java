package RoundTrip.repository;

import RoundTrip.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Custom query to search for recipes by name
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.recipeName) LIKE %:name%")
    List<Recipe> findRecipesByNameStartingWith(@Param("name") String name);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(i.ingredientName) LIKE %:ingredientName%")
    List<Recipe> findRecipesByIngredientName(@Param("ingredientName") String ingredientName);

}
