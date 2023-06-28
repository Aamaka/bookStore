package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByEmail(String email);

    Optional<Admin> findAdminByEmail(String email);
}
