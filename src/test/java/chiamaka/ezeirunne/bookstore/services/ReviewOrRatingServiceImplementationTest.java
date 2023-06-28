package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.ReviewAndRating;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.BookRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.ReviewRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.ReviewAndRatingDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

public class ReviewOrRatingServiceImplementationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewOrRatingServiceImplementation reviewOrRatingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void makeAReviewOrRating_ValidDto_ReturnsSuccessMessage() throws BookStoreException {
        // Arrange
        ReviewAndRatingDto dto = new ReviewAndRatingDto();
        dto.setCustomerId(1L);
        dto.setBookId(1L);
        dto.setComment("Great book!");
        dto.setRating(5);

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);

        ReviewAndRating reviewAndRating = new ReviewAndRating();
        // Set the properties of reviewAndRating

        Mockito.when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(bookRepository.findById(dto.getBookId())).thenReturn(Optional.of(book));
        Mockito.when(reviewRepository.existsByCustomer(customer.getId())).thenReturn(false);
        Mockito.when(reviewRepository.save(ArgumentMatchers.any(ReviewAndRating.class))).thenReturn(reviewAndRating);

        // Act
        String result = reviewOrRatingService.makeAReviewOrRating(dto);

        // Assert
        Assertions.assertEquals("Dropped successfully", result);
        // Assert any additional assertions for the behavior of the service
    }

    @Test
    public void makeAReviewOrRating_ExistingReviewOrRating_ThrowsBookStoreException() {
        ReviewAndRatingDto dto = new ReviewAndRatingDto();
        dto.setCustomerId(1L);
        dto.setBookId(1L);
        dto.setComment("Great book!");
        dto.setRating(5);

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);

        Mockito.when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(bookRepository.findById(dto.getBookId())).thenReturn(Optional.of(book));
        Mockito.when(reviewRepository.existsByCustomer(customer.getId())).thenReturn(true);

        Assertions.assertThrows(BookStoreException.class, () -> reviewOrRatingService.makeAReviewOrRating(dto));
    }

    @Test
    public void makeAReviewOrRating_InvalidCustomerId_ThrowsBookStoreException() {
        ReviewAndRatingDto dto = new ReviewAndRatingDto();
        dto.setCustomerId(1L);
        dto.setBookId(1L);
        dto.setComment("Great book!");
        dto.setRating(5);

        Mockito.when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.empty());

        Assertions.assertThrows(BookStoreException.class, () ->  reviewOrRatingService.makeAReviewOrRating(dto));
    }

    @Test
    public void makeAReviewOrRating_InvalidBookId_ThrowsBookStoreException() {
        // Arrange
        ReviewAndRatingDto dto = new ReviewAndRatingDto();
        dto.setCustomerId(1L);
        dto.setBookId(1L);
        dto.setComment("Great book!");
        dto.setRating(5);

        Customer customer = new Customer();
        customer.setId(1L);

        Mockito.when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(bookRepository.findById(dto.getBookId())).thenReturn(Optional.empty());

        Assertions.assertThrows(BookStoreException.class, () ->  reviewOrRatingService.makeAReviewOrRating(dto));
    }
}
