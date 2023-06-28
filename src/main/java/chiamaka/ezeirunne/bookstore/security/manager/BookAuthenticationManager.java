package chiamaka.ezeirunne.bookstore.security.manager;

import chiamaka.ezeirunne.bookstore.security.provider.BookAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Slf4j
public class BookAuthenticationManager implements AuthenticationManager {
    private final BookAuthenticationProvider bookAuthenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication manager => {}", authentication);
        return bookAuthenticationProvider.authenticate(authentication);
    }
}
