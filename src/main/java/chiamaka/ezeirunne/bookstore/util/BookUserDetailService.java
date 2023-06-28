package chiamaka.ezeirunne.bookstore.util;

import chiamaka.ezeirunne.bookstore.data.models.users.User;
import chiamaka.ezeirunne.bookstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService => {}", username);
        User user = userService.getUserByUserName(username);
        return new SecureUser(user);
    }
}
