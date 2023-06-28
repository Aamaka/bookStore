package chiamaka.ezeirunne.bookstore.data.models.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private int numberOfItem;

    private BigDecimal totalBookCost;
    private BigDecimal totalDeliveryCost;
    private BigDecimal totalCost;

    private String createdDate;

    private String modifiedDate;
}
