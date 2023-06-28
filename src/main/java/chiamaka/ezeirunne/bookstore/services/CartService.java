package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

public interface CartService {
    String addBookToCart (Long customerId, CartDto dto) throws BookStoreException;
    CartResponse viewCart(Long customerID) throws BookStoreException;
    String checkOut(Long customerId) throws BookStoreException;
}
