package RoundTrip.repository;

import RoundTrip.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorRepository extends JpaRepository<Editor, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Editor findByEmail(String email);

}
