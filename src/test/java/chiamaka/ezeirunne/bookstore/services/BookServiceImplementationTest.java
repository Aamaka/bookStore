package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookServiceImplementationTest {
    @Autowired
    private BookServiceImplementation bookServiceImplementation;

    @Test
    void registerBook() throws BookStoreException {
        BookRegistrationDto dto = new BookRegistrationDto();
        dto.setTitle("joy");
        dto.setAuthor("life");
        dto.setIsbn("Okay");
        dto.setCategory("FICTION");
        dto.setPrice(BigInteger.valueOf(6000));

        assertEquals("Registration successful", bookServiceImplementation.registerBook(dto));

    }

    @Test
    void findBookByTitle() {

    }

    @Test
    void findBooksByAuthor() {
    }

    @Test
    void findBooksByCategory() {
    }

    @Test
    void findAllBooks() {
    }
}