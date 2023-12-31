package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.dto.requests.UpdateBookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.PaginatedBookResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookServiceImplementationTest {
    @Autowired
    private BookServiceImplementation bookServiceImplementation;

    @Test
    void registerBook() throws BookStoreException {
        BookRegistrationDto dto = new BookRegistrationDto();
        dto.setTitle("joy");
        dto.setAuthor("Peace Amber");
        dto.setIsbn("879hgb");
        dto.setCategory("FICTION");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setQuantityOfBooksAvailable(400);
        dto.setDatePublished("12 july 2023");

        assertEquals("Registration successful", bookServiceImplementation.registerBook(dto));

    }

    @Test
    void findBookByTitle() throws BookStoreException {
        Book book = bookServiceImplementation.findBookByTitle("joy");
        assertEquals("life", book.getAuthor());
        assertThrows(BookStoreException.class, ()-> bookServiceImplementation.findBookByTitle("make"));
    }

    @Test
    void findBooksByAuthor() {
        PaginatedBookResponse response = bookServiceImplementation.findBooksByAuthor(1,1, "life");
        assertEquals("joy", response.getBookDtos().get(0).getBook().getTitle());
        assertThrows(BookStoreException.class, ()->bookServiceImplementation.findBooksByAuthor(1,1, "joy"));
    }

    @Test
    void findBooksByCategory() {
        PaginatedBookResponse response = bookServiceImplementation.findBooksByCategory(1, 1 , "FICTION");
        assertEquals(1, response.getBookDtos().size());
        assertThrows(BookStoreException.class, ()->bookServiceImplementation.findBooksByCategory(1,1, "SPIRITUAL"));

    }

    @Test
    void findAllBooks() {
        PaginatedBookResponse response = bookServiceImplementation.findAllBooks(1, 5);
        assertEquals(4, response.getBookDtos().size());
    }

    @Test
    void updateBook() throws BookStoreException {
        Long bookId = 3L;
        UpdateBookDto dto = new UpdateBookDto();
        dto.setPrice(BigDecimal.valueOf(300));
        assertEquals("Updated successfully", bookServiceImplementation.updateBook(bookId, dto));
        UpdateBookDto bookDto = new UpdateBookDto();
        Long book_id = 34L;
        bookDto.setPrice(BigDecimal.valueOf(100));
        assertThrows(BookStoreException.class, ()->bookServiceImplementation.updateBook(book_id, bookDto));
    }

    @Test
    void deleteBookByTitle() {
        assertThrows(BookStoreException.class,()-> bookServiceImplementation.deleteBookByTitle("Peace"));
    }

    @Test
    void deleteBookById() {
        assertThrows(BookStoreException.class,()-> bookServiceImplementation.deleteBookById(77));
    }

    @Test
    void deleteAll() {
        assertEquals("Deleted Successfully", bookServiceImplementation.deleteAll());
    }
}