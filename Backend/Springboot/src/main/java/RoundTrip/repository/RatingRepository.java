package RoundTrip.repository;

import RoundTrip.model.Rating;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByUsers(Users users);
    Rating findByRecipe(Recipe recipe);
    Rating findByUsersAndRecipe(Users user, Recipe recipe);

}
