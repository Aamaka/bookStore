package chiamaka.ezeirunne.bookstore.data.models;

import chiamaka.ezeirunne.bookstore.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private BigDecimal price;
    private String author;
    private int quantityOfBooksAvailable;
    private String isbn;
    private String datePublished;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String createdDate;
    private String modifiedDate;

}
