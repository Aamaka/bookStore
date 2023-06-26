package chiamaka.ezeirunne.bookstore.dto.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomerRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String gender;
    private String confirmPassword;
}
