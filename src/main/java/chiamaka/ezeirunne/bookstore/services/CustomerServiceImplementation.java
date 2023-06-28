package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.CustomerRegistrationDto;
import chiamaka.ezeirunne.bookstore.enums.Gender;
import chiamaka.ezeirunne.bookstore.enums.Authority;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String customerRegistration(CustomerRegistrationDto dto) throws BookStoreException {
        if(customerRepository.existsByEmail(dto.getEmail())) throw new BookStoreException("Email already exist");
        if(customerRepository.existsByPhoneNumber(dto.getPhoneNumber())) throw new BookStoreException("Phone number already exist");
        if(!dto.getPassword().equals(dto.getConfirmPassword())) throw new BookStoreException("Password Mismatch");
        Customer customer = modelMapper.map(dto, Customer.class);
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setGender(Gender.valueOf(dto.getGender()));
        customer.getAuthority().add((Authority.CUSTOMER));
        customer.setCreatedDate(LocalDateTime.now().toString());
        customerRepository.save(customer);
        return "Registration successful";
    }
}
