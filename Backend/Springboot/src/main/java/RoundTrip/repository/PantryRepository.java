package RoundTrip.repository;

import RoundTrip.model.Pantry;
import RoundTrip.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PantryRepository extends JpaRepository<Pantry, Long> {
    List<Pantry> findByUser(Users users);  // Custom query to find pantry items for a specific user

}
