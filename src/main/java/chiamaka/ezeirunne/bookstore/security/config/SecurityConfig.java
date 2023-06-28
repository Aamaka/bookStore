package chiamaka.ezeirunne.bookstore.security.config;

import chiamaka.ezeirunne.bookstore.security.filter.BookAuthenticationFilter;
import chiamaka.ezeirunne.bookstore.security.filter.BookAuthorizationFilter;
import chiamaka.ezeirunne.bookstore.security.jwt.JwtUtil;
import chiamaka.ezeirunne.bookstore.security.manager.BookAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final BookAuthenticationManager bookAuthenticationManager;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter filter =
                new BookAuthenticationFilter(bookAuthenticationManager, jwtUtil);
        filter.setFilterProcessesUrl("/api/book/users/login/");
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/admin/",
                        "/api/v1/customer/",
                        "/api/users/login/")
                .permitAll()
                .antMatchers("/api/v1/cart/check_out",
                        "/api/v1/cart/{customerId}",
                        "/api/v1/cart/",
                        "/api/v1/payment/","/api/v1/payment/{reference}",
                        "/api/v1/book/review_or_rating"
                )
                .hasAuthority("CUSTOMER")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/book/",
                        "/api/v1/book/update/{bookId}",
                        "/api/v1/book/delete")
                .hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/book/all/{title}",
                        "/api/v1/book/all")
                .permitAll()
                .and()
                .addFilter(filter)
                .addFilterBefore(new BookAuthorizationFilter(), BookAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().build();

    }

}
