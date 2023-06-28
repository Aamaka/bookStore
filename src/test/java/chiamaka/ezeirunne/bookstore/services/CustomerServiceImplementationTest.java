package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.CustomerRegistrationDto;
import chiamaka.ezeirunne.bookstore.enums.Gender;
import chiamaka.ezeirunne.bookstore.enums.Authority;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplementationTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImplementation customerService;

    @Test
    public void testCustomerRegistration_SuccessfulRegistration() throws BookStoreException {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setName("Rest");
        dto.setEmail("rest@gmail.com");
        dto.setPhoneNumber("1234567890");
        dto.setAddress("Earth");
        dto.setPassword("password");
        dto.setConfirmPassword("password");
        dto.setGender("MALE");

        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(dto.getPhoneNumber())).thenReturn(false);

        Customer customer = new Customer();
        when(modelMapper.map(dto, Customer.class)).thenReturn(customer);

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(encodedPassword);

        String result = customerService.customerRegistration(dto);

        verify(customerRepository).save(customer);

        assertEquals("Registration successful", result);
        assertEquals(encodedPassword, customer.getPassword());
        assertEquals(Gender.MALE, customer.getGender());
        assertNotNull(customer.getCreatedDate());
    }

    @Test
    public void testCustomerRegistration_EmailAlreadyExists() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setEmail("rest@gmail.com");
        when(customerRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BookStoreException.class, () -> customerService.customerRegistration(dto));
    }
}