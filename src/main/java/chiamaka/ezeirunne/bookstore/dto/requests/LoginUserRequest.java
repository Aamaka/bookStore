package chiamaka.ezeirunne.bookstore.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
