package chiamaka.ezeirunne.bookstore.util;

import chiamaka.ezeirunne.bookstore.data.models.users.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class SecureUser implements UserDetails {

    private final User user;
    @Override
    public String getUsername() {
        log.info("UserDetail => {}", user);
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthority()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
