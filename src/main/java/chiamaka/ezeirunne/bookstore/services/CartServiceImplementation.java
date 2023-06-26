package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.cart.Cart;
import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.BookRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CartItemRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CartRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.AddMultipleCartItemDto;
import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    private final BookRepository bookRepository;

    private final CartItemRepository cartItemRepository;
    private Customer getCustomerById(Long customerId) throws BookStoreException {
        return customerRepository.findById(customerId).orElseThrow(()-> new BookStoreException("Customer does not exist"));
    }

    private Book getBook(long bookId) throws BookStoreException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Book not found"));
    }

    private Cart getCustomerCart(Long customerId) throws BookStoreException {
        Customer customer = getCustomerById(customerId);
        Cart cart;

        if(cartRepository.existsByCustomerId((customerId))){
            cart = cartRepository.findByCustomerId((customerId)).get();
        }else {
            cart = Cart.builder()
                    .numberOfItem(0)
                    .customerId(customer.getId())
                    .totalCost(BigDecimal.ZERO)
                    .totalBookCost(BigDecimal.ZERO)
                    .totalDeliveryCost(BigDecimal.ZERO)
                    .createdDate(LocalDateTime.now().toString())
                    .build();
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public void addBookToCart(CartDto dto) throws BookStoreException {
        Cart cart = getCustomerCart(dto.getCustomerId());
        CartItem cartItem;

        BigDecimal totalBookCost = BigDecimal.ZERO;
        int totalNumberOfItems = 0;

        if (dto.getItems().isEmpty()) throw new BookStoreException("Item cannot be null");

        for (AddMultipleCartItemDto cartItemDto : dto.getItems()) {
            Book book = getBook(cartItemDto.getBookId());
            if(cartItemRepository.existsByBookId(book.getId())){
                cartItem = cartItemRepository.findByBookId(book.getId());
                cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.getQuantity());
                cartItem.setSubTotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                totalBookCost = totalBookCost.add(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                cartItem.setModifiedDate(LocalDateTime.now().toString());
                cartItemRepository.save(cartItem);
                totalNumberOfItems = totalNumberOfItems + cartItem.getQuantity() +  cartItemDto.getQuantity();
            }
            else {
                cartItem = new CartItem();

                cartItem.setBookId(book.getId());
                cartItem.setCartId(cart.getId());
                cartItem.setQuantity(cartItemDto.getQuantity());
                cartItem.setUnitCost(book.getPrice());
                cartItem.setSubTotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                cartItem.setCreatedDate(LocalDateTime.now().toString());
                cartItemRepository.save(cartItem);
                totalNumberOfItems = totalNumberOfItems + cartItemDto.getQuantity();
                totalBookCost = totalBookCost.add(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
        }
        cart.setNumberOfItem(totalNumberOfItems);
        cart.setTotalCost(totalBookCost);
        cart.setTotalBookCost(totalBookCost);
        cart.setModifiedDate(LocalDateTime.now().toString());
        cartRepository.save(cart);

    }

    @Override
    public CartResponse viewCart(long customerId) throws BookStoreException {
        CartResponse response = new CartResponse();
        Cart cart = getCustomerCart(customerId);
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        response.setCart(cart);
        response.setCartItems(cartItems);
        response.setNoOfItems(cart.getNumberOfItem());
        return response;

    }
}
