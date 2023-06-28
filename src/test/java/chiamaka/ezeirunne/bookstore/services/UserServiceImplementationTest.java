package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Admin;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.models.users.User;
import chiamaka.ezeirunne.bookstore.data.repositories.AdminRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.LoginUserRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceImplementationTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImplementation userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void login_ValidAdminCredentials_ReturnsAdminWelcomeMessage() {
        String email = "admin@gmail.com";
        String password = "admin123";
        LoginUserRequest request = new LoginUserRequest(email, password);

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));

        Mockito.when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.of(admin));
        Mockito.when(passwordEncoder.matches(password, admin.getPassword())).thenReturn(true);

        Response response = userService.login(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Welcome back " + admin.getName(), response.getMessage());
    }

    @Test
    public void login_ValidCustomerCredentials_ReturnsCustomerWelcomeMessage() {
        String email = "customer@gmail.com";
        String password = "customer123";
        LoginUserRequest request = new LoginUserRequest(email, password);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));

        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(Optional.of(customer));
        Mockito.when(passwordEncoder.matches(password, customer.getPassword())).thenReturn(true);

        Response response = userService.login(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Welcome back " + customer.getName(), response.getMessage());
    }

    @Test
    public void login_InvalidCredentials_ReturnsInvalidDetailsMessage() {
        String email = "user@gmail.com";
        String password = "password123";
        LoginUserRequest request = new LoginUserRequest(email, password);

        Mockito.when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(Optional.empty());

        Response response = userService.login(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Invalid details", response.getMessage());
    }

    @Test
    public void getUserByUserName_AdminExists_ReturnsAdmin() {
        String email = "admin@gmail.com";
        Admin admin = new Admin();
        admin.setEmail(email);

        Mockito.when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.of(admin));

        User user = userService.getUserByUserName(email);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(admin, user);
    }

    @Test
    public void getUserByUserName_CustomerExists_ReturnsCustomer() {
        String email = "customer@gmail.com";
        Customer customer = new Customer();
        customer.setEmail(email);

        Mockito.when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(Optional.of(customer));

        User user = userService.getUserByUserName(email);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(customer, user);
    }

    @Test
    public void getUserByUserName_UsernameNotFound_ThrowsUsernameNotFoundException() {
        String email = "nonexistent@gmail.com";

        Mockito.when(adminRepository.findAdminByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUserName(email));
    }
}
