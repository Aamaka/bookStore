package chiamaka.ezeirunne.bookstore.data.models.cart;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private int quantity;
    private BigDecimal subTotal;
    private BigDecimal unitCost;
    private long cartId;
    private String createdDate;
    private String modifiedDate;


}
