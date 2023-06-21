package chiamaka.ezeirunne.bookstore.controller;

import chiamaka.ezeirunne.bookstore.dto.requests.AdminRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import chiamaka.ezeirunne.bookstore.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/")
    public void adminRegistration (@RequestBody AdminRegistrationDto dto) throws BookStoreException {
        adminService.adminRegistration(dto);
    }
}
