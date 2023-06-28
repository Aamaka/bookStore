package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.data.models.users.User;
import chiamaka.ezeirunne.bookstore.dto.requests.LoginUserRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.Response;

public interface UserService {
    Response login(LoginUserRequest request);
    User getUserByUserName(String email);
}
