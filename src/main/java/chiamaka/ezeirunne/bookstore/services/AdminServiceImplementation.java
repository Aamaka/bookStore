package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Admin;
import chiamaka.ezeirunne.bookstore.data.repositories.AdminRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.AdminRegistrationDto;
import chiamaka.ezeirunne.bookstore.enums.Role;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdminServiceImplementation implements AdminService{

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void adminRegistration(AdminRegistrationDto dto) throws BookStoreException {
        if(adminRepository.existsByEmail(dto.getEmail())) throw new BookStoreException("Email already exist");
        if(!dto.getPassword().equals(dto.getConfirmPassword())) throw new BookStoreException("Password Mismatch");
        Admin admin = modelMapper.map(dto, Admin.class);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setCreatedDate(LocalDateTime.now().toString());
        adminRepository.save(admin);
    }
}
