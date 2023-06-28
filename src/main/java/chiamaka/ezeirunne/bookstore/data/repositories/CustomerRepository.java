package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Customer> findCustomerByEmail(String email);
}
