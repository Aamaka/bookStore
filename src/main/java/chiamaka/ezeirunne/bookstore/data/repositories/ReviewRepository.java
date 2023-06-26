package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.ReviewAndRating;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewAndRating, Long> {
    boolean existsByCustomer(Long customer);
}
