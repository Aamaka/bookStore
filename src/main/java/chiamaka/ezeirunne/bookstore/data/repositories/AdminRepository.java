package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);
}
