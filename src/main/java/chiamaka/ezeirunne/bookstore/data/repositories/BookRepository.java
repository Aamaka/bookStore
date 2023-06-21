package chiamaka.ezeirunne.bookstore.data.repositories;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleIsIgnoreCase(String title);

    Optional<Book> findByTitleIsIgnoreCase(String title);

    Page<Book> findBooksByAuthorIsIgnoreCase(String author, Pageable pageable);

    long countAllBooksByAuthorIsIgnoreCase(String author);

    Page<Book> findBooksByCategory(Category category, Pageable pageable);

    long countAllBooksByCategory(Category category);

    boolean existsByAuthorIsIgnoreCase(String author);

    boolean existsByCategory(Category category);
}
