package chiamaka.ezeirunne.bookstore.data.models;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewAndRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;
    private int rating;

    private String createdDate;

    private Long customer;

    private long bookId;
}
