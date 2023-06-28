package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.Admin;
import chiamaka.ezeirunne.bookstore.data.models.users.Customer;
import chiamaka.ezeirunne.bookstore.data.models.users.User;
import chiamaka.ezeirunne.bookstore.data.repositories.AdminRepository;
import chiamaka.ezeirunne.bookstore.data.repositories.CustomerRepository;
import chiamaka.ezeirunne.bookstore.dto.requests.LoginUserRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Response login(LoginUserRequest request) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(request.getEmail());
        if(admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPassword()))
            return response(admin.get());

        Optional<Customer> customer = customerRepository.findCustomerByEmail(request.getEmail());
        if(customer.isPresent() && passwordEncoder.matches(request.getPassword(), customer.get().getPassword()))
            return response(customer.get());

        return Response.builder()
                .message("Invalid details")
                .build();
    }

    private Response response(User user) {
        return Response.builder()
                .message("Welcome back "+ user.getName() )
                .build();
    }

    @Override
    public User getUserByUserName(String email) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(email);
        if(admin.isPresent()) return admin.get();

        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if(customer.isPresent()) return customer.get();

        throw new UsernameNotFoundException("Username not found");
    }
}
