package chiamaka.ezeirunne.bookstore.dto.responses;

import chiamaka.ezeirunne.bookstore.data.models.cart.Cart;
import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private Cart cart;
    private List<CartItem> cartItems;
    private long noOfItems;
    private long totalNoOfCartItems;
    private BigDecimal totalCost;
    private BigDecimal totalBookCost;
}
