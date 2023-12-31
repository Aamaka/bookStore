package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartId(long cartId);

    boolean existsByBookIdAndCartId(Long bookId, Long cartId);

    CartItem findByBookIdAndCartId(Long bo, Long cartId);

    void deleteByCartId(Long id);

    List<CartItem> findAllByBookId(long bookId);
}
