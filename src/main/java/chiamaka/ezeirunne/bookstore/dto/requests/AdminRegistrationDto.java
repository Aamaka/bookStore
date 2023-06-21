package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegistrationDto {
    private String name;

    private String email;

    private String password;
    private String confirmPassword;
}
