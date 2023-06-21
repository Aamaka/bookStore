package chiamaka.ezeirunne.bookstore.dto.requests;


import chiamaka.ezeirunne.bookstore.enums.Gender;
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
    private Gender gender;
    private String confirmPassword;
}
