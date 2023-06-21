package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
