package backendLogin.roundTrip.login.repository;

import backendLogin.roundTrip.login.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
