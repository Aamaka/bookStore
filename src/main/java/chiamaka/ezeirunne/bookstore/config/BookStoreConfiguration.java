package chiamaka.ezeirunne.bookstore.config;

import chiamaka.ezeirunne.bookstore.security.jwt.JwtUtil;
import com.auth0.jwt.algorithms.Algorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BookStoreConfiguration {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(jwtIssuer, Algorithm.HMAC512(jwtSecret));
    }
}
