package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.dto.requests.ReviewAndRatingDto;
import chiamaka.ezeirunne.bookstore.dto.requests.UpdateBookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.PaginatedBookResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

import java.io.IOException;


public interface BookService {
    String registerBook(BookRegistrationDto bookRegistrationDto) throws IOException, BookStoreException;
    Book findBookByTitle(String title) throws BookStoreException;
    PaginatedBookResponse findBooksByAuthor(int pageNumber, int limit, String author) throws BookStoreException;
    PaginatedBookResponse findBooksByCategory(int pageNumber, int limit, String category) throws BookStoreException;
    PaginatedBookResponse findAllBooks(int pageNumber, int limit);
    String updateBook (Long bookId, UpdateBookDto dto) throws BookStoreException;
    String deleteBookByTitle (String title) throws BookStoreException;
    String deleteBookById (long id) throws BookStoreException;
    String deleteAll();



}
