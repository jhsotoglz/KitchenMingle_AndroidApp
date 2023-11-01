package RoundTrip.repository;

import RoundTrip.model.Comment;
import RoundTrip.model.Rating;
import RoundTrip.model.Recipe;
import RoundTrip.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserName(String username);
    List<Comment> findByUser(Users users);
    List<Comment> findByRecipe(Recipe recipe);
    List<Comment> findByUsersAndRecipe(Users user, Recipe recipe);
}
