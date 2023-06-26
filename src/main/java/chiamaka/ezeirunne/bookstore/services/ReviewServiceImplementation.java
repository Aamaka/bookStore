package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.ReviewAndRating;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.BookRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.ReviewRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.ReviewAndRatingDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReviewServiceImplementation implements ReviewService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;

    private Book getBook(long bookId) throws BookStoreException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Book not found"));
    }

    private Customer getCustomerById(Long customerId) throws BookStoreException {
        return customerRepository.findById(customerId).orElseThrow(()-> new BookStoreException("Customer does not exist"));
    }
    @Override
    public String makeAReview(ReviewAndRatingDto dto) throws BookStoreException {
        Customer customer = getCustomerById(dto.getCustomerId());
        Book book = getBook(dto.getBookId());
        ReviewAndRating reviewAndRating = new ReviewAndRating();
        if (reviewRepository.existsByCustomer(customer.getId())) throw new BookStoreException("Review or rating already done");
        reviewAndRating.setComment(dto.getComment());
        reviewAndRating.setRating(dto.getRating());
        reviewAndRating.setCreatedDate(LocalDateTime.now().toString());
        reviewAndRating.setBookId(book.getId());
        reviewAndRating.setCustomer(customer.getId());

        reviewRepository.save(reviewAndRating);
        return "Dropped successfully";
    }
}
