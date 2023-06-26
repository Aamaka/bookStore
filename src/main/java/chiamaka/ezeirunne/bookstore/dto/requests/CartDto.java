package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long customerId;
    private BigDecimal totalCost;
    private List<AddMultipleCartItemDto> items;
}
