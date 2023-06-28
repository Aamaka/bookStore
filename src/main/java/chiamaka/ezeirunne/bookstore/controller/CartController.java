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
    public String addBookToCart (@RequestBody CartDto dto) throws BookStoreException{
        return cartService.addBookToCart(dto);
    }
    @GetMapping("/{customerId}")
    public CartResponse viewCart(@PathVariable Long customerId) throws BookStoreException{
        return cartService.viewCart(customerId);
    }

    @PostMapping("/check_out")
    public String checkOut(@RequestParam Long customerId) throws BookStoreException{
        return cartService.checkOut(customerId);
    }
}
