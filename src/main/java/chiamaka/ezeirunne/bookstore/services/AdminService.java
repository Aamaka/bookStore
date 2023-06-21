package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.AdminRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;

public interface AdminService {
    void adminRegistration(AdminRegistrationDto dto) throws BookStoreException;
}
