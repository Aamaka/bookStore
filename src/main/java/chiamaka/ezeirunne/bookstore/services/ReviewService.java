package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.ReviewAndRatingDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

public interface ReviewService {

    String makeAReview(ReviewAndRatingDto dto) throws BookStoreException;
}
