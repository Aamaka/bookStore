package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

public interface CartService {
    void addBookToCart (CartDto dto) throws BookStoreException;
    CartResponse viewCart(long customerID) throws BookStoreException;
}
