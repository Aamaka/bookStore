package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Long customerId;
    private int quantity;
    private long bookId;
}
