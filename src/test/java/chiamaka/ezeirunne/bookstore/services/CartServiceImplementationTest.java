package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.AddMultipleCartItemDto;
import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CartServiceImplementationTest {

    @Autowired
    CartServiceImplementation cartServiceImplementation;

    @Test
    void addBookToCart() throws BookStoreException {
        List<AddMultipleCartItemDto> itemDto = new ArrayList<>();
        AddMultipleCartItemDto itemDto1 = new AddMultipleCartItemDto();
        itemDto1.setQuantity(2);
        itemDto1.setBookId(2);
        itemDto.add(itemDto1);

        AddMultipleCartItemDto itemDto2 = new AddMultipleCartItemDto();
        itemDto2.setQuantity(2);
        itemDto2.setBookId(3);
        itemDto.add(itemDto2);

        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(1L);
        cartDto.setItems(itemDto);
        cartServiceImplementation.addBookToCart(cartDto);
    }

    @Test
    void viewCart() throws BookStoreException {
        CartResponse response = cartServiceImplementation.viewCart(1);
        assertEquals(2, response.getCartItems().size());
    }

}