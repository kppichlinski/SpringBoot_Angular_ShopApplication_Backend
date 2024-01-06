package com.example.shop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.shop.security.dto.LoginCredentials;
import com.example.shop.security.dto.RegisterCredentials;
import com.example.shop.security.dto.ShopUserDetails;
import com.example.shop.security.dto.Token;
import com.example.shop.security.model.User;
import com.example.shop.security.model.UserRole;
import com.example.shop.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private static final String BCRYPT = "{bcrypt}";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Value("${jwt.expirationTime}")
    private long expirationTime;
    @Value("${jwt.secret}")
    private String secret;

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
    }

    @PostMapping("/register")
    public Token register(@RequestBody RegisterCredentials registerCredentials) {
        String password = registerCredentials.getPassword();
        String repeatPassword = registerCredentials.getRepeatPassword();
        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("Passwords are not identical!");
        }

        boolean userExists = userRepository.existsByUsername(registerCredentials.getUsername());
        if (userExists) {
            throw new IllegalArgumentException("User with given username already exists in database!");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(registerCredentials.getPassword());

        List<UserRole> roles = List.of(UserRole.ROLE_CUSTOMER);
        User user = User.builder()
                .username(registerCredentials.getUsername())
                .password(BCRYPT + passwordEncoded)
                .enabled(true)
                .authorities(roles)
                .build();

        userRepository.save(user);
        return authenticate(registerCredentials.getUsername(), registerCredentials.getPassword());
    }

    private Token authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authToken);

        ShopUserDetails principal = (ShopUserDetails) authenticate.getPrincipal();

        String subject = String.valueOf(principal.getId());
        Date expiresAt = new Date(System.currentTimeMillis() + expirationTime);
        Algorithm sign = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(expiresAt)
                .sign(sign);

        boolean adminAccess = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> UserRole.ROLE_ADMIN.name().equals(s))
                .map(s -> true)
                .findFirst()
                .orElse(false);

        return new Token(token, adminAccess);
    }
}
