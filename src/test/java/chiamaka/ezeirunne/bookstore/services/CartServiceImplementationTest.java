package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CartServiceImplementationTest {

    @Autowired
    CartServiceImplementation cartServiceImplementation;

    @Test
    void addBookToCart() throws BookStoreException {
        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(12L);
        cartDto.setBookId(2);
        cartDto.setQuantity(4);
        cartServiceImplementation.addBookToCart(cartDto);
    }

    @Test
    void viewCart() throws BookStoreException {
        CartResponse response = cartServiceImplementation.viewCart(1);
        assertEquals(2, response.getCartItems().size());
    }

}