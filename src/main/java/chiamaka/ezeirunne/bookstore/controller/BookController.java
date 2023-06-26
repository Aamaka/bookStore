package chiamaka.ezeirunne.bookstore.controller;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.dto.requests.UpdateBookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.PaginatedBookResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import chiamaka.ezeirunne.bookstore.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/book")
@Slf4j
public class BookController {
    @Autowired
    private  BookService bookService;

    @PostMapping("/")
    public ResponseEntity<String> bookRegistration(@RequestBody BookRegistrationDto dto) throws IOException, BookStoreException {
        return new ResponseEntity<>(bookService.registerBook(dto), HttpStatus.CREATED);
    }

    @GetMapping("/all/{title}")
    public ResponseEntity<Book> findBooksByTitle(@PathVariable String title) throws BookStoreException {
        return new ResponseEntity<>(bookService.findBookByTitle(title), HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedBookResponse> findAllBooks(@RequestParam(required = false, defaultValue = "1") int page,
                                                             @RequestParam(required = false, defaultValue = "1") int limit,
                                                             @RequestParam(required = false) String category,
                                                             @RequestParam(required = false) String author) throws BookStoreException {
        PaginatedBookResponse response;
        if(author != null) {
            response = bookService.findBooksByAuthor(page, limit , author);

        } else if (category != null) {
            response = bookService.findBooksByCategory(page, limit, category);

        } else  {
            response = bookService.findAllBooks(page, limit);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateBook(@RequestBody UpdateBookDto dto) throws BookStoreException {
        return new ResponseEntity<>(bookService.updateBook(dto), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public  ResponseEntity<String> deleteBook (@RequestParam(required = false) String title,
                                               @RequestParam(required = false) long id) throws BookStoreException {
        String response;
        if(title != null) {
            response =  bookService.deleteBookByTitle(title);
        } else if (id == 0) {
            response = bookService.deleteBookById(id);
        } else  {
            response = bookService.deleteAll();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
