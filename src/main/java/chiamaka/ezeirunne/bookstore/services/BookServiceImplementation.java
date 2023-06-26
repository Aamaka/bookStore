package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.cart.Cart;
import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import chiamaka.ezeirunne.bookstore.data.repositories.BookRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CartItemRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CartRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.BookRegistrationDto;
import chiamaka.ezeirunne.bookstore.dto.requests.UpdateBookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.BookDto;
import chiamaka.ezeirunne.bookstore.dto.responses.PaginatedBookResponse;
import chiamaka.ezeirunne.bookstore.enums.Category;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;


    @Override
    public String registerBook(BookRegistrationDto dto) throws BookStoreException {
        if(bookRepository.existsByTitleIsIgnoreCase(dto.getTitle())) throw new BookStoreException("Book already exist");
        Book book = modelMapper.map(dto, Book.class);
        book.setCategory(Category.valueOf(dto.getCategory()));
        book.setCreatedDate(LocalDateTime.now().toString());
        bookRepository.save(book);
        return "Registration successful";
    }

    @Override
    public Book findBookByTitle(String title) throws BookStoreException {
        return bookRepository.findByTitleIsIgnoreCase(title).orElseThrow(() ->new BookStoreException("Book not found"));
    }

    @Override
    public PaginatedBookResponse findBooksByAuthor(int pageNumber, int limit, String author) {
        PaginatedBookResponse response = new PaginatedBookResponse();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by(order));
        List<BookDto> books = new ArrayList<>();
        Page<Book> bookPage = bookRepository.findBooksByAuthorIsIgnoreCase(author, pageable);
        setBookDetails(pageNumber, response, books, bookPage);
        response.setNumberOfBooks(bookRepository.countAllBooksByAuthorIsIgnoreCase(author));
        response.setNoOfTotalPages(bookRepository.countAllBooksByAuthorIsIgnoreCase(author) / limit);
        return response;
    }

    @Override
    public PaginatedBookResponse findBooksByCategory(int pageNumber, int limit, String category) {
        PaginatedBookResponse response = new PaginatedBookResponse();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by(order));
        List<BookDto> books = new ArrayList<>();
        Page<Book> bookPage = bookRepository.findBooksByCategory(Category.valueOf(category), pageable);
        // todo refactoring
        setBookDetails(pageNumber, response, books, bookPage);
        response.setNumberOfBooks(bookRepository.countAllBooksByCategory(Category.valueOf(category)));
        response.setNoOfTotalPages(bookRepository.countAllBooksByCategory(Category.valueOf(category)) / limit);
        return response;
    }

    private void setBookDetails(int pageNumber, PaginatedBookResponse response, List<BookDto> books, Page<Book> bookPage) {
        for (Book book : bookPage) {
            BookDto bookDto = new BookDto();
            bookDto.setBook(book);
            //todo  link to the order of books when done
            //todo implement login when security is done
            bookDto.setNumberOfOrders(0);
            bookDto.setNumberOfBooks(book.getQuantityOfBooksAvailable());
            books.add(bookDto);
        }
        response.setBookDtos(books);
        response.setCurrentPage(pageNumber);
    }

    @Override
    public PaginatedBookResponse findAllBooks(int pageNumber, int limit) {
        PaginatedBookResponse response = new PaginatedBookResponse();
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by(order));
        List<BookDto> books = new ArrayList<>();
        Page<Book> bookPage = bookRepository.findAll(pageable);
        setBookDetails(pageNumber, response, books, bookPage);
        response.setNumberOfBooks(bookRepository.count());
        response.setNoOfTotalPages(bookRepository.count() / limit);
        return response;
    }

    @Override
    public String updateBook(UpdateBookDto dto) throws BookStoreException {
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new BookStoreException("Book does not exist"));
        if(dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }
        if(dto.getAuthor() != null) {
            book.setAuthor(dto.getAuthor());
        }
        if(dto.getIsbn() != null) {
            book.setIsbn(dto.getIsbn());
        }
        if(dto.getDatePublished() != null) {
            book.setDatePublished(dto.getDatePublished());
        }
        if(dto.getPrice() != null) {
//            BigDecimal totalBookCost = BigDecimal.ZERO;
            book.setPrice(dto.getPrice());
//            CartItem cartItem = cartItemRepository.findByBookId(dto.getBookId());
//            cartItem.setUnitCost(dto.getPrice());
//            cartItem.setSubTotal(dto.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
//            totalBookCost = totalBookCost.add(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
//            cartItem.setModifiedDate(LocalDateTime.now().toString());
//            cartItemRepository.save(cartItem);
//            Cart cart = cartRepository.findById(cartItem.getCartId()).get();
//            cart.setTotalCost(totalBookCost);
//            cart.setTotalBookCost(totalBookCost);
//            cart.setModifiedDate(LocalDateTime.now().toString());
//            cartRepository.save(cart);
        }
        if(dto.getQuantityOfBooksAvailable() != 0) {
            book.setQuantityOfBooksAvailable(dto.getQuantityOfBooksAvailable());
        }
        if(dto.getCategory() != null) {
            book.setCategory(Category.valueOf(dto.getCategory()));
        }
        book.setModifiedDate(LocalDateTime.now().toString());
        bookRepository.save(book);
        return "Updated successfully";
    }

    @Override
    public String deleteBookByTitle(String title) throws BookStoreException {
        bookRepository.delete(bookRepository.findByTitleIsIgnoreCase(title)
                                            .orElseThrow(() -> new BookStoreException(
                                                    String.format("Book with title %s does noe exist", title))));
        return "Deleted Successfully";
    }

    @Override
    public String deleteBookById(long id) throws BookStoreException {
        bookRepository.delete(bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(
                        String.format("Book with id %d doest not exist", id))));
        return "Deleted Successfully";
    }

    public String deleteAll(){
        bookRepository.deleteAll();
        return "Deleted Successfully";
    }

}