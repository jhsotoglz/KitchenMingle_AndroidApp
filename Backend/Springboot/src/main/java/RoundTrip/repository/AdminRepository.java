package RoundTrip.repository;

import RoundTrip.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository  extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Admin findByEmail(String email);
}
