package chiamaka.ezeirunne.bookstore.controller;

import chiamaka.ezeirunne.bookstore.dto.requests.CustomerRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import chiamaka.ezeirunne.bookstore.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1//customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public void customerRegistration(@RequestBody CustomerRegistrationDto dto) throws BookStoreException {
        customerService.customerRegistration(dto);
    }
}
