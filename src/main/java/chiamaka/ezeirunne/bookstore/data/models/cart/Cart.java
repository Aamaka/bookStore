package chiamaka.ezeirunne.bookstore.data.models.cart;

import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private Long customerId;

    private int numberOfItem;

    private BigDecimal totalBookCost;
    private BigDecimal totalDeliveryCost;
    private BigDecimal totalCost;

    private String createdDate;

    private String modifiedDate;


}
