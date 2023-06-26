package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewAndRatingDto {
    private String comment;
    private int rating;
    private long bookId;
    private long customerId;
}
