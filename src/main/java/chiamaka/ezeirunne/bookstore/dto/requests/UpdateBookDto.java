package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class UpdateBookDto {
    private long bookId;
    private String title;
    private BigDecimal price;
    private String author;
    private String isbn;
    private int quantityOfBooksAvailable;
    private String datePublished;
    private String category;
}

