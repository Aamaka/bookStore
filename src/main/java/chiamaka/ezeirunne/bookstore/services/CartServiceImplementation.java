package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import chiamaka.ezeirunne.bookstore.data.models.Order;
import chiamaka.ezeirunne.bookstore.data.models.OrderItem;
import chiamaka.ezeirunne.bookstore.data.models.cart.Cart;
import chiamaka.ezeirunne.bookstore.data.models.cart.CartItem;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.*;
import chiamaka.ezeirunne.bookstore.dto.requests.CartDto;
import chiamaka.ezeirunne.bookstore.dto.responses.CartResponse;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    private final BookRepository bookRepository;

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private Customer getCustomerById(Long customerId) throws BookStoreException {
        return customerRepository.findById(customerId).orElseThrow(()-> new BookStoreException("Customer does not exist"));
    }

    Book getBook(Long bookId) throws BookStoreException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Book not found"));
    }

    public Cart getCustomerCart(Long customerId) throws BookStoreException {
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
                    .createdDate(LocalDateTime.now().toString())
                    .build();
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public String addBookToCart(Long customerId, CartDto dto) throws BookStoreException {
        System.out.println("i===> I GOT HERE");
        Cart cart = getCustomerCart(customerId);
        CartItem cartItem;

        BigDecimal totalBookCost = BigDecimal.ZERO;
        int totalNumberOfItems = 0;

        Book book = getBook(dto.getBookId());
        if(cartItemRepository.existsByBookIdAndCartId(book.getId(), cart.getId())){
            cartItem = cartItemRepository.findByBookIdAndCartId(book.getId(), cart.getId());
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
            cartItem.setSubTotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItem.setModifiedDate(LocalDateTime.now().toString());
            cartItemRepository.save(cartItem);
        }
        else {
            cartItem = new CartItem();
            cartItem.setBookId(book.getId());
            cartItem.setCartId(cart.getId());
            cartItem.setQuantity(dto.getQuantity());
            cartItem.setUnitCost(book.getPrice());
            cartItem.setSubTotal(book.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItem.setCreatedDate(LocalDateTime.now().toString());
            cartItemRepository.save(cartItem);
        }
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        for (CartItem cartItem1 : cartItems){
            totalBookCost = totalBookCost.add(cartItem1.getSubTotal());
            totalNumberOfItems = totalNumberOfItems + cartItem1.getQuantity();
        }
        cart.setNumberOfItem(totalNumberOfItems);
        cart.setTotalCost(totalBookCost);
        cart.setTotalBookCost(totalBookCost);
        cart.setModifiedDate(LocalDateTime.now().toString());
        cartRepository.save(cart);
        return "Success";
    }

    @Override
    public CartResponse viewCart(Long customerId) throws BookStoreException {
        CartResponse response = new CartResponse();
        Cart cart = getCustomerCart(customerId);
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        response.setCart(cart);
        response.setCartItems(cartItems);
        response.setNoOfItems(cart.getNumberOfItem());
        response.setTotalCost(cart.getTotalCost());
        response.setTotalNoOfCartItems(cartItems.size());
        response.setTotalBookCost(cart.getTotalBookCost());
        return response;

    }

    @Override
    public String checkOut(Long customerId) throws BookStoreException {
        Cart cart = getCustomerCart(customerId);
        if(cart.getNumberOfItem() == 0) throw new BookStoreException("Cart is empty");
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());

        for (CartItem cartItem: cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(getBook(cartItem.getBookId()));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

        }
        order.setOrderItems(orderItems);
        order.setCreatedDate(LocalDateTime.now().toString());
        order.setTotalCost(cart.getTotalCost());
        order.setNumberOfItem(cart.getNumberOfItem());
        order.setTotalBookCost(cart.getTotalBookCost());
        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
        cartRepository.delete(cart);

        return "Checked out successfully";
    }
}
