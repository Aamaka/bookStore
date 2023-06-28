package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.order.Order;
import chiamaka.ezeirunne.bookstore.data.models.cart.Cart;
import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.*;
import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CartServiceImplementationTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private  BookRepository bookRepository;
    private CartServiceImplementation cartService;
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartServiceImplementation(customerRepository, cartRepository, bookRepository,  cartItemRepository, orderRepository);
    }

    @Test
    void testAddBookToCart_ExistingCartItem() throws BookStoreException {
        Long customerId = 1L;
        Long bookId = 1L;
        int quantity = 2;

        Customer customer = new Customer();
        customer.setId(customerId);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomerId(customerId);


        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.valueOf(10));

        CartItem existingCartItem = new CartItem();
        existingCartItem.setBookId(bookId);
        existingCartItem.setCartId(cart.getId());
        existingCartItem.setQuantity(1);
        existingCartItem.setUnitCost(book.getPrice());
        existingCartItem.setSubTotal(book.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));

        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(cartRepository.existsByCustomerId(eq(customerId))).thenReturn(true);
        when(cartRepository.findByCustomerId(eq(customerId))).thenReturn(Optional.of(cart));
        when(cartItemRepository.existsByBookIdAndCartId(eq(bookId), eq(cart.getId()))).thenReturn(true);
        when(cartItemRepository.findByBookIdAndCartId(eq(bookId), eq(cart.getId()))).thenReturn(existingCartItem);


        String result = cartService.addBookToCart(bookId, new CartDto(customerId, quantity));


        verify(cartItemRepository, times(1)).findByBookIdAndCartId(eq(bookId), eq(cart.getId()));
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
        assertEquals("Success", result);
        assertEquals(3, existingCartItem.getQuantity());
        assertEquals(BigDecimal.valueOf(30), existingCartItem.getSubTotal());

    }

    @Test
    void testAddBookToCart_NewCartItem() throws BookStoreException {
        Long customerId = 1L;
        Long bookId = 2L;
        int quantity = 1;

        Customer customer = new Customer();
        customer.setId(customerId);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomerId(customerId);

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.valueOf(15));

        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(cartRepository.existsByCustomerId(eq(customerId))).thenReturn(true);
        when(cartRepository.findByCustomerId(eq(customerId))).thenReturn(Optional.of(cart));
        when(cartItemRepository.existsByBookIdAndCartId(eq(bookId), eq(cart.getId()))).thenReturn(false);

        String result = cartService.addBookToCart(customerId, new CartDto(bookId, quantity));


        verify(cartItemRepository, never()).findByBookIdAndCartId(anyLong(), anyLong());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
        assertEquals("Success", result);
    }

    @Test
    void testViewCart() throws BookStoreException {
        Long customerId = 1L;
        Long bookId = 2L;

        Customer customer = new Customer();
        customer.setId(customerId);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomerId(customerId);

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.valueOf(10.0));


        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setBookId(bookId);
        cartItem1.setQuantity(2);
        cartItems.add(cartItem1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setBookId(2L);
        cartItem2.setQuantity(1);
        cartItems.add(cartItem2);


        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(cartRepository.existsByCustomerId(eq(customerId))).thenReturn(true);
        when(cartRepository.findByCustomerId(eq(customerId))).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(eq(cart.getId()))).thenReturn(cartItems);


        CartResponse response = cartService.viewCart(customerId);

        verify(cartItemRepository, times(1)).findAllByCartId(eq(cart.getId()));
        assertEquals(cart, response.getCart());
        assertEquals(cartItems, response.getCartItems());
        assertEquals(2, response.getTotalNoOfCartItems());
    }

    @Test
    void testCheckOut_CartNotEmpty() throws BookStoreException {

        Long customerId = 1L;
        Long bookId = 2L;


        Customer customer = new Customer();
        customer.setId(customerId);

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(BigDecimal.valueOf(10.0));
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomerId(customerId);
        cart.setNumberOfItem(2);
        cart.setTotalCost(BigDecimal.valueOf(50));
        cart.setTotalBookCost(BigDecimal.valueOf(40));

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setBookId(bookId);
        cartItem1.setQuantity(2);
        cartItems.add(cartItem1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setBookId(2L);
        cartItem2.setQuantity(1);
        cartItems.add(cartItem2);

        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(cartRepository.existsByCustomerId(eq(customerId))).thenReturn(true);
        when(cartRepository.findByCustomerId(eq(customerId))).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(eq(cart.getId()))).thenReturn(cartItems);


        String result = cartService.checkOut(customerId);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartItemRepository, times(1)).deleteAll(eq(cartItems));
        verify(cartRepository, times(1)).delete(eq(cart));
        assertEquals("Checked out successfully", result);
    }

    @Test
    void testCheckOut_CartEmpty() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomerId(customerId);
        cart.setNumberOfItem(0);


        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(cartRepository.existsByCustomerId(eq(customerId))).thenReturn(true);
        when(cartRepository.findByCustomerId(eq(customerId))).thenReturn(Optional.of(cart));

        assertThrows(BookStoreException.class, () -> cartService.checkOut(customerId));
        verify(orderRepository, never()).save(any(Order.class));
        verify(cartItemRepository, never()).deleteAll(anyList());
        verify(cartRepository, never()).delete(any(Cart.class));
    }
}
