package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.repositories.BookRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.dto.requests.UpdateBookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.PaginatedBookResponse;
import chiamaka.ezeirunne.bookstore.enums.Category;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplementationMockTest {

        @Mock
        private BookRepository bookRepository;

        @Mock
        private ModelMapper modelMapper;

        @InjectMocks
        private BookServiceImplementation bookService;

        @Test
        public void testRegisterBook_SuccessfulRegistration() throws BookStoreException {
            BookRegistrationDto dto = new BookRegistrationDto();
            dto.setPrice(BigDecimal.valueOf(5000.0));
            dto.setTitle("Book Title");
            dto.setCategory("FICTION");
            dto.setDatePublished("21st march 2015 ");
            dto.setIsbn("34568901124678954");
            dto.setQuantityOfBooksAvailable(400);
            dto.setAuthor("Scholz Lode");

            when(bookRepository.existsByTitleIsIgnoreCase(dto.getTitle())).thenReturn(false);

            Book book = new Book();
            when(modelMapper.map(dto, Book.class)).thenReturn(book);

            String result = bookService.registerBook(dto);

            verify(bookRepository).save(book);

            assertEquals("Registration successful", result);
            assertEquals(Category.FICTION, book.getCategory());
            assertNotNull(book.getCreatedDate());
        }

        @Test
        public void testRegisterBook_BookAlreadyExists() {
            BookRegistrationDto dto = new BookRegistrationDto();
            dto.setTitle("Book Title");
            dto.setCategory("SELF_HELP");
            dto.setDatePublished("21st march 2015 ");
            dto.setIsbn("3456855554678954");
            dto.setQuantityOfBooksAvailable(200);
            dto.setAuthor("Scholz Lode");

            when(bookRepository.existsByTitleIsIgnoreCase(dto.getTitle())).thenReturn(true);

            assertThrows(BookStoreException.class, ()-> bookService.registerBook(dto));
        }





    @Test
    public void testUpdateBook_SuccessfulUpdate() throws BookStoreException {
        UpdateBookDto dto = new UpdateBookDto();
        dto.setId(1L);
        dto.setTitle("James Griffin");
        dto.setAuthor("Peter Mark");

        Book existingBook = new Book();
        existingBook.setId(dto.getId());
        existingBook.setTitle("Book TItle");
        existingBook.setAuthor("Scholz Lode");

        when(bookRepository.findById(dto.getId())).thenReturn(Optional.of(existingBook));


        String result = bookService.updateBook(dto);

        verify(bookRepository).save(existingBook);


        assertEquals("Updated successfully", result);
        assertEquals("James Griffin", existingBook.getTitle());
        assertEquals("Peter Mark", existingBook.getAuthor());

        assertNotNull(existingBook.getModifiedDate());
    }

    @Test
    public void testUpdateBook_BookNotFound()  {
        UpdateBookDto dto = new UpdateBookDto();
        dto.setId(3L);
        dto.setTitle("Sole");
        dto.setCategory("SELF_HELP");
        dto.setDatePublished("21st march 2015 ");
        dto.setIsbn("3456855554678954");
        dto.setQuantityOfBooksAvailable(200);
        dto.setAuthor("Scholz Lode");


        when(bookRepository.findById(dto.getId())).thenReturn(Optional.empty());

        assertThrows(BookStoreException.class, ()-> bookService.updateBook(dto));
    }


    @Test
    public void testFindByTitle_BookFound() throws BookStoreException {
        String title = "James Griffin";
        Book book = new Book();
        book.setTitle(title);
        book.setCategory(Category.valueOf("SELF_HELP"));
        book.setDatePublished("21st march 2015 ");
        book.setIsbn("3456855554678954");
        book.setQuantityOfBooksAvailable(200);
        book.setAuthor("Scholz Lode");


        when(bookRepository.findByTitleIsIgnoreCase(title)).thenReturn(Optional.of(book));

         Book result = bookService.findBookByTitle(title);

         assertEquals(book, result);
    }

    @Test
    public void testFindByTitle_BookNotFound() {
        String title = "Luck";

        when(bookRepository.findByTitleIsIgnoreCase(title)).thenReturn(Optional.empty());

        assertThrows(BookStoreException.class, ()-> bookService.findBookByTitle(title));
    }

    @Test
    public void testFindByCategory_BooksFound() {
        String category = "SELF_HELP";
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setCategory(Category.FICTION);
        book.setDatePublished("21st march 2015 ");
        book.setIsbn("3456855554678954");
        book.setQuantityOfBooksAvailable(200);
        book.setAuthor("Scholz Lode");
        books.add(book);


        when(bookRepository.findBooksByCategory(eq(Category.valueOf(category)), any(Pageable.class)))
                .thenReturn(new PageImpl<>(books));

        PaginatedBookResponse result = bookService.findBooksByCategory(1, 10, category);

        assertEquals(books.size(), result.getBookDtos().size());
    }

    @Test
    public void testFindByCategory_BooksNotFound() {
        String category = "SELF_HELP";

        assertThrows(NullPointerException.class, ()-> bookService.findBooksByCategory(1, 10, category));
    }

    @Test
    public void testFindByAuthor_BooksFound() {
        String author = "Peter Mark";
        List<Book> books = new ArrayList<>();

        when(bookRepository.findBooksByAuthorIsIgnoreCase(eq(author), any(Pageable.class)))
                .thenReturn(new PageImpl<>(books));

        PaginatedBookResponse result = bookService.findBooksByAuthor(1, 10, author);

        assertEquals(books.size(), result.getBookDtos().size());
    }

    @Test
    public void testFindByAuthor_BooksNotFound() {
        String author = "Ojo";

        assertThrows(NullPointerException.class, ()-> bookService.findBooksByAuthor(1, 10, author));

    }
}




