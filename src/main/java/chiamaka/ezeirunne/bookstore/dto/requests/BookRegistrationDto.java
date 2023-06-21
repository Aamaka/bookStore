package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class BookRegistrationDto {
    private String title;
    private BigInteger price;
    private String author;
    private String isbn;
    private int quantityOfBooksAvailable;
    private String datePublished;
    private String category;
}
