package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.CustomerRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

public interface CustomerService {
    void customerRegistration(CustomerRegistrationDto dto) throws BookStoreException;
}
