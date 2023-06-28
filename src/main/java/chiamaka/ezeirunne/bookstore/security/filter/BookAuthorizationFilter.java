package chiamaka.ezeirunne.bookstore.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class BookAuthorizationFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (request.getServletPath().equals("/api/user/login/")) {
            filterChain.doFilter(request, response);
        } else {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring("Bearer".length());
                    JWTVerifier jwtVerifier = JWT
                            .require(Algorithm.HMAC512("bookApp"))
                            .ignoreIssuedAt()
                            .build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token.substring(1));
                    String subject = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("role").asList(String.class);
                    UsernamePasswordAuthenticationToken passwordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(subject, null, roles
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .toList());

                    SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setContentType(APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(response.getOutputStream(), e.getMessage());
                }
            } else filterChain.doFilter(request, response);
        }

    }

}
