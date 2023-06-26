package chiamaka.ezeirunne.bookstore.controller;

import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import chiamaka.ezeirunne.bookstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public void addBookToCart (@RequestBody CartDto dto) throws BookStoreException{
        cartService.addBookToCart(dto);
    }
    @GetMapping("/{customerId}")
    public CartResponse viewCart(@PathVariable long customerId) throws BookStoreException{
        return cartService.viewCart(customerId);
    }
}
