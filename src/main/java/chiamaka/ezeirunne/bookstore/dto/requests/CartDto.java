package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private Long customerId;
    private Long bookId;
    private int quantity;
}
