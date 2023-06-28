package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Admin;
import chiamaka.ezeirunne.bookstore.data.repositories.AdminRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.AdminRegistrationDto;
import chiamaka.ezeirunne.bookstore.exceptions.BookStoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminServiceImplementationTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImplementation adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adminRegistration_SuccessfulRegistration() throws BookStoreException {
        AdminRegistrationDto dto = new AdminRegistrationDto();
        dto.setEmail("admin@gmail.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");

        Admin admin = new Admin();
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        admin.setCreatedDate(LocalDateTime.now().toString());

        Mockito.when(adminRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(modelMapper.map(dto, Admin.class)).thenReturn(admin);
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("hashedPassword");

        ArgumentCaptor<Admin> adminCaptor = ArgumentCaptor.forClass(Admin.class);

        adminService.adminRegistration(dto);

        Mockito.verify(adminRepository).existsByEmail(dto.getEmail());
        Mockito.verify(adminRepository).save(adminCaptor.capture());

        Admin capturedAdmin = adminCaptor.getValue();
        Assertions.assertEquals(dto.getEmail(), capturedAdmin.getEmail());
        Assertions.assertEquals("hashedPassword", capturedAdmin.getPassword());
        Assertions.assertEquals(admin.getCreatedDate(), capturedAdmin.getCreatedDate());
    }

    @Test
    public void adminRegistration_EmailAlreadyExists_ThrowsException() {
        AdminRegistrationDto dto = new AdminRegistrationDto();
        dto.setEmail("admin@gmail.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");

        Mockito.when(adminRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BookStoreException.class, () -> adminService.adminRegistration(dto));

        Mockito.verify(adminRepository).existsByEmail(dto.getEmail());
        Mockito.verify(adminRepository, Mockito.never()).save(Mockito.any(Admin.class));
    }

    @Test
    public void adminRegistration_PasswordMismatch_ThrowsException() {
        AdminRegistrationDto dto = new AdminRegistrationDto();
        dto.setEmail("admin@gmail.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("differentPassword");

        assertThrows(BookStoreException.class, () -> adminService.adminRegistration(dto));


        Mockito.verify(adminRepository, Mockito.never()).save(Mockito.any(Admin.class));
    }
}
